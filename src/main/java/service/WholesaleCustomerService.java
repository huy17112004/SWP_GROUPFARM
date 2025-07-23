package service;

import dao.WholesaleCustomerDAO;
import dto.RegisterRequestDTO;
import dto.SignInRequestDTO;
import entity.WholesaleCustomer;
import jakarta.persistence.EntityManager;
import util.EmailUtil;
import util.JwtUtil;
import util.TemplateUtil;
import validation.AccountValidation;
import org.mindrot.jbcrypt.BCrypt;
import util.JpaUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WholesaleCustomerService {

    /**
     * SIGNUP với luồng:
     * 1. Validate cú pháp (không check unique email/username)
     * 2. Mở transaction:
     *    - Nếu email đã ACTIVE → lỗi
     *    - Nếu email PENDING → update và resend verify
     *    - Nếu email mới → check username, tạo mới PENDING và gửi verify
     */
    public void signup(RegisterRequestDTO req) {
        // 1. Validate cú pháp
        AccountValidation.validateSyntax(
                req.getUsername(),
                req.getEmail(),
                req.getPassword(),
                req.getConfirmPassword()
        );

        // 2. Transactional block
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            WholesaleCustomerDAO dao = new WholesaleCustomerDAO(em);

            // 2a. Kiểm tra email theo status
            WholesaleCustomer existing = dao.findByEmail(req.getEmail());
            if (existing != null) {
                if ("ACTIVE".equals(existing.getStatus())) {
                    throw new IllegalArgumentException("Email đã tồn tại");
                }
                // NHÁNH PENDING: kiểm tra username
                WholesaleCustomer other = dao.findByUsername(req.getUsername());
                if (other != null && !other.getEmail().equals(req.getEmail())) {
                    throw new IllegalArgumentException("Username đã tồn tại");
                }
                // Cập nhật và commit
                existing.setUsername(req.getUsername());
                existing.setContactPerson(req.getUsername());
                existing.setPassword(BCrypt.hashpw(req.getPassword(), BCrypt.gensalt()));
                dao.createOrUpdate(existing);
                em.getTransaction().commit();

                // Gửi mail xác thực bất đồng bộ
                new Thread(() -> sendVerificationEmail(existing, req.getVerificationBaseUrl())).start();
                return;
            }

            // 2b. Email mới → check username
            if (dao.findByUsername(req.getUsername()) != null) {
                throw new IllegalArgumentException("Username đã tồn tại");
            }

            // 2c. Tạo mới PENDING và commit
            WholesaleCustomer c = new WholesaleCustomer();
            c.setUsername(req.getUsername());
            c.setContactPerson(req.getUsername());
            c.setEmail(req.getEmail());
            c.setPassword(BCrypt.hashpw(req.getPassword(), BCrypt.gensalt()));
            c.setStatus("PENDING");
            dao.createOrUpdate(c);
            em.getTransaction().commit();

            // Gửi mail xác thực bất đồng bộ
            new Thread(() -> sendVerificationEmail(c, req.getVerificationBaseUrl())).start();

        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }


    /**
     * Gửi email xác thực kèm token JWT
     */
    private void sendVerificationEmail(WholesaleCustomer c, String verificationBaseUrl) {
        try {
            String token = JwtUtil.generateEmailToken(c.getEmail());
            String verifyUrl = verificationBaseUrl + "?token=" + token;

            Map<String, String> vars = new HashMap<>();
            vars.put("username", c.getContactPerson());
            vars.put("verifyUrl", verifyUrl);

            String html;
            try {
                html = TemplateUtil.render("templates/welcome.html", vars);
            } catch (IOException e) {
                html = "<p>Xin chào " + c.getContactPerson() + "</p>"
                        + "<p>Vui lòng <a href=\"" + verifyUrl + "\">click vào đây</a> để xác thực tài khoản.</p>";
            }

            EmailUtil.sendHtmlEmail(
                    c.getEmail(),
                    "Xác thực tài khoản Fastkart",
                    html
            );
        } catch (Exception e) {
            System.err.println("❌ Lỗi gửi email xác thực: " + e.getMessage());
        }
    }

    /**
     * Verify link: đọc token → lấy email → set status=ACTIVE
     */
    public boolean verify(String token) {
        String email = JwtUtil.validateAndGetEmail(token);
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            WholesaleCustomerDAO dao = new WholesaleCustomerDAO(em);
            WholesaleCustomer c = dao.findByEmail(email);
            if (c != null && "PENDING".equals(c.getStatus())) {
                c.setStatus("ACTIVE");
                dao.createOrUpdate(c);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Sign-In qua email/password, chỉ khi status = ACTIVE
     */
    public WholesaleCustomer login(SignInRequestDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            WholesaleCustomerDAO dao = new WholesaleCustomerDAO(em);
            WholesaleCustomer c = dao.findByUsername(dto.getUsername());
            if (c == null) {
                throw new IllegalArgumentException("Username không tồn tại");
            }
            if (!"ACTIVE".equals(c.getStatus())) {
                throw new IllegalStateException("Tài khoản chưa được kích hoạt");
            }
            if (!BCrypt.checkpw(dto.getPassword(), c.getPassword())) {
                throw new IllegalArgumentException("Mật khẩu không đúng");
            }
            return c;
        } finally {
            em.close();
        }
    }
    public void sendPasswordResetLink(String email, String resetBaseUrl) {
        WholesaleCustomer c = new WholesaleCustomerDAO(JpaUtil.getEntityManager())
                .findByEmail(email);
        if (c == null) {
            throw new IllegalArgumentException("Email không tồn tại");
        }

        String token = JwtUtil.generatePasswordResetToken(email);
        String link  = resetBaseUrl + "?token=" + token;

        // build html
        String html = "<p>Xin chào " + c.getContactPerson() + ",</p>"
                + "<p>Click vào <a href=\"" + link + "\">đây</a> để đặt lại mật khẩu. Link chỉ hiệu lực "
                + (JwtUtil.EXP_MS/1000/60) + " phút.</p>";

        new Thread(() -> {
            try {
                EmailUtil.sendHtmlEmail(
                        email,
                        "Yêu cầu đặt lại mật khẩu",
                        html
                );
            } catch (Exception ex) {
                // log lỗi gửi mail
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * Thực sự đặt lại mật khẩu khi user submit form mới
     */
    public void resetPasswordByToken(String token, String newPassword) {
        String email = JwtUtil.validateAndGetEmailFromResetToken(token);
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            WholesaleCustomerDAO dao = new WholesaleCustomerDAO(em);
            WholesaleCustomer c = dao.findByEmail(email);
            if (c == null) {
                throw new IllegalArgumentException("Tài khoản không tồn tại");
            }
            c.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            dao.createOrUpdate(c);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
