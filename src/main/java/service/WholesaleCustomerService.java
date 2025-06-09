package service;

import dao.WholesaleCustomerDAO;
import entity.WholesaleCustomer;

public class WholesaleCustomerService {
    private final WholesaleCustomerDAO customerDAO = new WholesaleCustomerDAO();

    public WholesaleCustomer login(String username, String password) {
        // Có thể thêm validate hoặc mã hóa password tại đây nếu cần
        return customerDAO.findByUsernameAndPassword(username, password);
    }
}
