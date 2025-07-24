// src/main/java/service/RoleAuthService.java
package service;

import dao.*;
import entity.*;
import org.mindrot.jbcrypt.BCrypt;
import util.JpaUtil;
import jakarta.persistence.EntityManager;

public class RoleAuthService {
    public Account loginWithRole(String username, String password, String role) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            AccountDAO accountDAO = new AccountDAO(em);
            Account acc = accountDAO.findByUsername(username);
            if (acc == null || !BCrypt.checkpw(password, acc.getPassword())) {
                throw new IllegalArgumentException("Tài khoản hoặc mật khẩu không đúng");
            }

            switch (role.toUpperCase()) {
                case "SELLER":
                    if (new SellerDAO(em).findByAccountId(acc.getId()) == null)
                        throw new IllegalStateException("Bạn không có quyền truy cập với vai trò quản lí");
                    break;
                case "MANAGER":
                    if (new ManagerDAO(em).findByAccountId(acc.getId()) == null)
                        throw new IllegalStateException("Bạn không có quyền truy cập với vai trò quản lí");
                    break;
                case "WAREHOUSE":
                    if (new WarehouseStaffDAO(em).findByAccountId(acc.getId()) == null)
                        throw new IllegalStateException("Bạn không có quyền nhân viên kho");
                    break;
                case "SHIPPER":
                    if (new ShipperDAO(em).findByAccountId(acc.getId()) == null)
                        throw new IllegalStateException("Bạn không có quyền giao hàng");
                    break;
                default:
                    throw new IllegalArgumentException("Vai trò không hợp lệ");
            }

            return acc;
        } finally {
            em.close();
        }
    }
}
