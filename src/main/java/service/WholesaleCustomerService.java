package service;

import dao.WholesaleCustomerDAO;
import entity.WholesaleCustomer;
import validation.AccountValidation;
import org.mindrot.jbcrypt.BCrypt;

public class WholesaleCustomerService {
    private final WholesaleCustomerDAO customerDAO = new WholesaleCustomerDAO();

    public WholesaleCustomer login(String username, String password) {
        // Có thể thêm validate hoặc mã hóa password tại đây nếu cần
        return customerDAO.findByUsernameAndPassword(username, password);
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
}
