// src/main/java/service/WarehouseStaffService.java
package service;

import dto.WarehouseStaffRequestDTO;
import entity.Warehouse;
import entity.WarehouseStaff;
import org.mindrot.jbcrypt.BCrypt;
import util.JpaUtil;

import jakarta.persistence.EntityManager;
import java.util.Date;

public class WarehouseStaffService {

    public WarehouseStaff createStaff(WarehouseStaffRequestDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            // Validate username
            if (dto.getUsername() == null || dto.getUsername().isBlank())
                throw new IllegalArgumentException("Tên tài khoản không được để trống!");

            // Username đã tồn tại?
            Long count = em.createQuery(
                            "SELECT COUNT(a) FROM Account a WHERE a.username = :username", Long.class)
                    .setParameter("username", dto.getUsername())
                    .getSingleResult();
            if (count > 0) throw new IllegalArgumentException("Username đã tồn tại!");

            // Validate email
            if (dto.getEmail() == null || dto.getEmail().isBlank())
                throw new IllegalArgumentException("Email không được để trống!");
            if (!dto.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
                throw new IllegalArgumentException("Email không hợp lệ!");

            // Email đã tồn tại?
            count = em.createQuery(
                            "SELECT COUNT(w) FROM WarehouseStaff w WHERE w.email = :email", Long.class)
                    .setParameter("email", dto.getEmail())
                    .getSingleResult();
            if (count > 0) throw new IllegalArgumentException("Email đã tồn tại!");

            // Validate phone
            if (dto.getPhone() == null || dto.getPhone().isBlank())
                throw new IllegalArgumentException("Số điện thoại không được để trống!");
            if (!dto.getPhone().matches("^0\\d{9,10}$"))
                throw new IllegalArgumentException("Số điện thoại không hợp lệ!");

            // Validate password
            if (dto.getRawPassword() == null || dto.getRawPassword().length() < 6)
                throw new IllegalArgumentException("Mật khẩu phải từ 6 ký tự!");

            // Validate name
            if (dto.getName() == null || dto.getName().isBlank())
                throw new IllegalArgumentException("Tên không được để trống!");

            // Validate warehouse
            Warehouse wh = em.find(Warehouse.class, dto.getWarehouseId());
            if (wh == null) throw new IllegalArgumentException("Kho không tồn tại!");

            // --- Tạo tài khoản ---
            em.getTransaction().begin();
            WarehouseStaff staff = new WarehouseStaff();
            staff.setUsername(dto.getUsername());
            staff.setPassword(BCrypt.hashpw(dto.getRawPassword(), BCrypt.gensalt()));
            staff.setName(dto.getName());
            staff.setEmail(dto.getEmail());
            staff.setPhone(dto.getPhone());
            staff.setCreatedAt(new Date());
            staff.setWarehouse(wh);
            em.persist(staff);
            em.getTransaction().commit();
            return staff;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

}
