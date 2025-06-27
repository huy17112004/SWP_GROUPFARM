package service;

import dao.WholeSaleCustomerDAO;
import dto.WholeSaleCustomerDTO;
import entity.WholesaleCustomer;
import validation.AccountValidation;
import org.mindrot.jbcrypt.BCrypt;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

public class WholesaleCustomerService {


    public WholesaleCustomer login(String username, String password) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            WholeSaleCustomerDAO customerDAO = new WholeSaleCustomerDAO(em);
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
        var tx = em.getTransaction();

        try {
            tx.begin();

            WholeSaleCustomerDAO customerDAO = new WholeSaleCustomerDAO(em);

            // Xác thực dữ liệu đầu vào
            AccountValidation.validateAccountCreation(username, email, password, WholesaleCustomer.class);


            if (customerDAO.findByUsername(username) != null) {
                throw new IllegalArgumentException("Username already exists");
            }
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


            customerDAO.createAccount(customer);

            tx.commit();

            return customer;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback(); // rollback nếu có lỗi
            }
            throw new RuntimeException("Signup failed: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    public void updateInfor(){
        EntityManager em = JpaUtil.getEntityManager();

    }
}
