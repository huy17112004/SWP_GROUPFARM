package service;

import dao.WholesaleCustomerDAO;
import entity.WholesaleCustomer;
import validation.AccountValidation;
import org.mindrot.jbcrypt.BCrypt;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

public class WholesaleCustomerService {

    // LOGIN
    public WholesaleCustomer login(String username, String password) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            WholesaleCustomerDAO customerDAO = new WholesaleCustomerDAO(em);
            WholesaleCustomer customer = customerDAO.findByUsername(username);

            if (customer != null && BCrypt.checkpw(password, customer.getPassword())) {
                return customer;
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    // SIGNUP
    public WholesaleCustomer signup(String username, String email, String password) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            WholesaleCustomerDAO customerDAO = new WholesaleCustomerDAO(em);

            // Xác thực dữ liệu đầu vào
            AccountValidation.validateAccountCreation(username, email, password, WholesaleCustomer.class);

            // Kiểm tra username đã tồn tại chưa
            if (customerDAO.findByUsername(username) != null) {
                throw new IllegalArgumentException("Username already exists");
            }

            // Kiểm tra email đã tồn tại chưa
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
        } finally {
            em.close();
        }
    }
}
