package service;

import dao.WholesaleCustomerDAO;
import entity.WholesaleCustomer;
import util.EmailUtil;
import validation.AccountValidation;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;

public class WholesaleCustomerService {
    private final WholesaleCustomerDAO customerDAO = new WholesaleCustomerDAO();

    public WholesaleCustomer login(String username, String password) {
        WholesaleCustomer customer = customerDAO.findByUsername(username);

        if (customer != null && BCrypt.checkpw(password, customer.getPassword())) {
            return customer;
        } else {
            return null;
        }
    }

    // SIGNUP
    public WholesaleCustomer signup(String username, String email, String password) {
        // Xác thực dữ liệu đầu vào
        AccountValidation.validateAccountCreation(username, email, password, WholesaleCustomer.class);

            //Kiểm tra xem username đã tồn tại chưa
            if (customerDAO.findByUsernameAndPassword(username, null) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Kiểm tra xem email đã tồn tại chưa
        if (customerDAO.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Mã hóa mật khẩu
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Tạo đối tượng khách hàng mới
        WholesaleCustomer customer = new WholesaleCustomer();
        customer.setUsername(username);
        customer.setEmail(email);
        customer.setPassword(hashedPassword);

        // Lưu khách hàng vào cơ sở dữ liệu
        customerDAO.createAccount(customer);

        // Trả về khách hàng đã tạo
        return customer;
    }

    public void sendOtp(String email) {
        WholesaleCustomer customer = customerDAO.findByEmail(email);
        if (customer == null) {
            throw new IllegalArgumentException("Email not found");
        }

        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);

        customer.setOtp(otp);
        customer.setOtpExpiredAt(expiryTime);
        customerDAO.createOrUpdate(customer);

        String subject = "Your OTP Code";
        String content = "Your OTP code is: " + otp + "\nIt will expire in 5 minutes.";
        EmailUtil.sendEmail(email, subject, content);
    }

    public boolean verifyOtp(String email, String otp) {
        WholesaleCustomer customer = customerDAO.findByEmail(email);
        if (customer == null || customer.getOtp() == null) {
            return false;
        }

        return customer.getOtp().equals(otp)
                && customer.getOtpExpiredAt().isAfter(LocalDateTime.now());
    }

    public void resetPassword(String email, String otp, String newPassword) {
        if (!verifyOtp(email, otp)) {
            throw new IllegalArgumentException("OTP is invalid or expired");
        }

        WholesaleCustomer customer = customerDAO.findByEmail(email);
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        customer.setPassword(hashedPassword);
        customer.setOtp(null);
        customer.setOtpExpiredAt(null);

        customerDAO.createOrUpdate(customer);
    }
}
