package service;

import dao.SellerDAO;
import dao.WholesaleOrderDAO;
import dto.SellerDTO;
import dto.SellerRequestDTO;
import entity.Seller;
import jakarta.persistence.EntityManager;
import org.mindrot.jbcrypt.BCrypt;
import util.JpaUtil;

import java.util.*;
import java.util.stream.Collectors;

public class SellerService {
    public Seller selectBestSeller(EntityManager em) {

        WholesaleOrderDAO wholesaleOrderDAO = new WholesaleOrderDAO(em);
        SellerDAO sellerDAO = new SellerDAO(em);

        List<Seller> activeSellers = sellerDAO.getActiveSellers();

        if (activeSellers.isEmpty()) {
            throw new RuntimeException("Không có seller nào đang hoạt động.");
        }

        Map<Integer, Long> negotiatingMap = wholesaleOrderDAO.getNegotiatingCountBySeller();

        // Map<User, Long>
        Map<Seller, Long> sellerCountMap = new HashMap<>();
        for (Seller seller : activeSellers) {
            long count = negotiatingMap.getOrDefault(seller.getId(), 0L);
            sellerCountMap.put(seller, count);
        }

        // Tìm min
        long minCount = sellerCountMap.values().stream().min(Long::compare).orElse(0L);

        // Lọc seller có số lượng bằng minCount
        List<Seller> bestSellers = sellerCountMap.entrySet().stream()
                .filter(entry -> entry.getValue() == minCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Trả về 1 người ngẫu nhiên trong nhóm ít đơn nhất
        Collections.shuffle(bestSellers);
        return bestSellers.get(0);
    }

    // Lấy danh sách Seller
    public List<SellerDTO> getAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT new dto.SellerDTO(s.id, s.username, s.name, s.email, s.phone, s.status) FROM Seller s";
            return em.createQuery(jpql, SellerDTO.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Tạo mới Seller
    public void create(SellerRequestDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            // Check trùng username, email nếu cần
            Long count = em.createQuery("SELECT COUNT(a) FROM Account a WHERE a.username = :username", Long.class)
                    .setParameter("username", dto.getUsername())
                    .getSingleResult();
            if (count > 0) throw new IllegalArgumentException("Username đã tồn tại!");

            count = em.createQuery("SELECT COUNT(s) FROM Seller s WHERE s.email = :email", Long.class)
                    .setParameter("email", dto.getEmail())
                    .getSingleResult();
            if (count > 0) throw new IllegalArgumentException("Email đã tồn tại!");

            Seller seller = new Seller();
            seller.setUsername(dto.getUsername());
            seller.setPassword(BCrypt.hashpw(dto.getRawPassword(), BCrypt.gensalt()));
            seller.setName(dto.getName());
            seller.setEmail(dto.getEmail());
            seller.setPhone(dto.getPhone());
            seller.setCreatedAt(new Date());
            seller.setStatus("ACTIVE"); // mặc định ACTIVE
            em.persist(seller);

            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    // Sửa Seller (không sửa password nếu trống)
    public void update(int id, SellerRequestDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Seller seller = em.find(Seller.class, id);
            if (seller == null) throw new IllegalArgumentException("Không tìm thấy tài khoản!");

            // Kiểm tra email đã tồn tại?
            Long count = em.createQuery("SELECT COUNT(s) FROM Seller s WHERE s.email = :email AND s.id <> :id", Long.class)
                    .setParameter("email", dto.getEmail())
                    .setParameter("id", id)
                    .getSingleResult();
            if (count > 0) throw new IllegalArgumentException("Email đã tồn tại!");

            seller.setName(dto.getName());
            seller.setEmail(dto.getEmail());
            seller.setPhone(dto.getPhone());
            if (dto.getRawPassword() != null && !dto.getRawPassword().isBlank()) {
                seller.setPassword(BCrypt.hashpw(dto.getRawPassword(), BCrypt.gensalt()));
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    // Xóa Seller (soft delete)
    public void delete(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Seller seller = em.find(Seller.class, id);
            if (seller != null) {
                seller.setStatus("DEPENDING"); // Soft delete*
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
