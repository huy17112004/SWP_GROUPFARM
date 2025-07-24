package service;

import dao.ShipperDAO;
import dto.ShipperDTO;
import dto.ShipperNameDTO;
import dto.ShipperRequestDTO;
import entity.Shipper;
import entity.WholesaleOrder;
import jakarta.persistence.EntityManager;
import org.mindrot.jbcrypt.BCrypt;
import util.JpaUtil;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ShipperService {
    public List<ShipperNameDTO> getShippers() {
        EntityManager em = JpaUtil.getEntityManager();
        ShipperDAO shipperDAO = new ShipperDAO(em);
        return shipperDAO.findAll().stream().map(ShipperNameDTO::new).collect(Collectors.toList());
    }
    public List<ShipperDTO> getAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT new dto.ShipperDTO(s.id, s.username, s.name, s.status) FROM Shipper s";
            return em.createQuery(jpql, ShipperDTO.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Tạo mới Shipper
    public void create(ShipperRequestDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            // Check trùng username
            Long count = em.createQuery("SELECT COUNT(a) FROM Account a WHERE a.username = :username", Long.class)
                    .setParameter("username", dto.getUsername())
                    .getSingleResult();
            if (count > 0) throw new IllegalArgumentException("Username đã tồn tại!");

            Shipper shipper = new Shipper();
            shipper.setUsername(dto.getUsername());
            shipper.setPassword(BCrypt.hashpw(dto.getRawPassword(), BCrypt.gensalt()));
            shipper.setName(dto.getName());
            shipper.setCreatedAt(new Date());
            shipper.setStatus("ACTIVE"); // mặc định ACTIVE
            em.persist(shipper);

            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    // Sửa Shipper (không cho sửa username, không đổi password nếu trống)
    public void update(int id, ShipperRequestDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Shipper shipper = em.find(Shipper.class, id);
            if (shipper == null) throw new IllegalArgumentException("Không tìm thấy tài khoản!");

            shipper.setName(dto.getName());
            if (dto.getRawPassword() != null && !dto.getRawPassword().isBlank()) {
                shipper.setPassword(BCrypt.hashpw(dto.getRawPassword(), BCrypt.gensalt()));
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    // Xóa Shipper (soft delete)
    public void delete(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Shipper shipper = em.find(Shipper.class, id);
            if (shipper != null) {
                shipper.setStatus("DEPENDING"); // Soft delete
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

