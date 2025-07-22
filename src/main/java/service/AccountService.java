package service;

import dao.*;
import entity.*;
import dto.LoginResponseDTO;
import org.mindrot.jbcrypt.BCrypt;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

public class AccountService {

    public LoginResponseDTO login(String username, String password) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Account account = null;
            String accountType = null;
            String name = null;

            // ADMIN
            Admin admin = new AdminDAO(em).findByUsername(username);
            if (tryLogin(admin, password)) {
                account = admin;
                accountType = "ADMIN";
            }

            // SALER
            if (account == null) {
                Seller saler = new SellerDAO(em).findByUsername(username);
                if (tryLogin(saler, password)) {
                    account = saler;
                    accountType = "SALER";
                    name = saler.getName();
                }
            }

            // MANAGER
            if (account == null) {
                Manager manager = new ManagerDAO(em).findByUsername(username);
                if (tryLogin(manager, password)) {
                    account = manager;
                    accountType = "MANAGER";
                    name = manager.getName();
                }
            }

            // SHIPPER
            if (account == null) {
                Shipper shipper = new ShipperDAO(em).findByUsername(username);
                if (tryLogin(shipper, password)) {
                    account = shipper;
                    accountType = "SHIPPER";
                    name = shipper.getName();
                }
            }

            // WAREHOUSE MANAGER
            if (account == null) {
                WarehouseManager wm = new WarehouseManagerDAO(em).findByUsername(username);
                if (tryLogin(wm, password)) {
                    account = wm;
                    accountType = "WAREHOUSE_MANAGER";
                    name = wm.getName();
                }
            }

            // WAREHOUSE STAFF
            if (account == null) {
                WarehouseStaff ws = new WarehouseStaffDAO(em).findByUsername(username);
                if (tryLogin(ws, password)) {
                    account = ws;
                    accountType = "WAREHOUSE_STAFF";
                    name = ws.getName();
                }
            }

            // WHOLESALE CUSTOMER
            if (account == null) {
                WholesaleCustomer customer = new WholesaleCustomerDAO(em).findByUsername(username);
                if (tryLogin(customer, password)) {
                    account = customer;
                    accountType = "WHOLESALE_CUSTOMER";
                }
            }

            // Trả kết quả
            if (account != null) {
                return new LoginResponseDTO(
                        "Login successfully",
                        true,
                        accountType,
                        account.getId(),
                        account.getUsername(),
                        name);
            } else {
                return new LoginResponseDTO(
                        "Invalid username or password",
                        false,
                        null,
                        0,
                        null,
                        null);
            }

        } finally {
            em.close();
        }
    }

    // Hàm kiểm tra mật khẩu an toàn
    private boolean tryLogin(Account account, String rawPassword) {
        if (account == null || account.getPassword() == null) return false;
        try {
            return BCrypt.checkpw(rawPassword, account.getPassword());
        } catch (IllegalArgumentException e) {
            // hash không hợp lệ (ví dụ: chưa mã hóa bằng BCrypt)
            return false;
        }
    }
}