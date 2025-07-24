package service;

import dao.WarehouseStaffDAO;
import dto.WarehouseStaffListDTO;
import dto.WarehouseStaffRequestDTO;
import entity.Warehouse;
import entity.WarehouseStaff;
import org.mindrot.jbcrypt.BCrypt;
import util.JpaUtil;

import jakarta.persistence.EntityManager;
import org.mindrot.jbcrypt.BCrypt;
import util.JpaUtil;

import java.util.Date;
import java.util.List;

public class WarehouseStaffService {
    public WarehouseStaff getWarehouseStaffById(int warehouseStaffId) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        try {
            WarehouseStaffDAO warehouseStaffDAO = new WarehouseStaffDAO(entityManager);
            return warehouseStaffDAO.findById(warehouseStaffId);
        } finally {
            entityManager.close();
        }
    }

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
    public List<WarehouseStaffListDTO> getAllStaff() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT new dto.WarehouseStaffListDTO(s.id, s.username, s.name, s.email, s.phone, w.warehouseName, w.id) "
                    + "FROM WarehouseStaff s JOIN s.warehouse w";
            return em.createQuery(jpql, WarehouseStaffListDTO.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Lấy chi tiết 1 staff theo id
    public WarehouseStaffListDTO getStaffById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT new dto.WarehouseStaffListDTO(s.id, s.username, s.name, s.email, s.phone, w.warehouseName, w.id) "
                    + "FROM WarehouseStaff s JOIN s.warehouse w WHERE s.id = :id";
            return em.createQuery(jpql, WarehouseStaffListDTO.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    // Sửa thông tin nhân viên (không sửa password nếu để trống)
    public void updateStaff(int id, WarehouseStaffRequestDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            WarehouseStaff staff = em.find(WarehouseStaff.class, id);
            if (staff == null) throw new IllegalArgumentException("Nhân viên không tồn tại!");

            // Check email đã tồn tại chưa (bỏ qua nếu đúng email của chính nhân viên này)
            Long count = em.createQuery(
                            "SELECT COUNT(s) FROM WarehouseStaff s WHERE s.email = :email AND s.id != :id", Long.class)
                    .setParameter("email", dto.getEmail())
                    .setParameter("id", id)
                    .getSingleResult();
            if (count > 0) throw new IllegalArgumentException("Email đã tồn tại!");

            // Update thông tin
            staff.setName(dto.getName());
            staff.setEmail(dto.getEmail());
            staff.setPhone(dto.getPhone());
            // Nếu cho phép đổi warehouse:
            if (dto.getWarehouseId() != staff.getWarehouse().getId()) {
                Warehouse wh = em.find(Warehouse.class, dto.getWarehouseId());
                if (wh == null) throw new IllegalArgumentException("Kho không tồn tại!");
                // Check kho đã có staff chưa
                Long staffCount = em.createQuery(
                                "SELECT COUNT(ws) FROM WarehouseStaff ws WHERE ws.warehouse.id = :wid", Long.class)
                        .setParameter("wid", dto.getWarehouseId())
                        .getSingleResult();
                if (staffCount > 0)
                    throw new IllegalArgumentException("Kho này đã có nhân viên quản lý!");
                staff.setWarehouse(wh);
            }
            // Nếu đổi mật khẩu (tùy nhu cầu, nếu có field rawPassword và không rỗng mới đổi)
            if (dto.getRawPassword() != null && !dto.getRawPassword().isBlank()) {
                staff.setPassword(BCrypt.hashpw(dto.getRawPassword(), BCrypt.gensalt()));
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    // Xóa nhân viên
    public void deleteStaff(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            WarehouseStaff staff = em.find(WarehouseStaff.class, id);
            if (staff != null) {
                em.remove(staff);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
