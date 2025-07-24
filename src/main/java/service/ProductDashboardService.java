package service;

import dao.ProductDashboardDAO;
import dto.ProductDashboardDTO;
import entity.Category;
import entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import util.JpaUtil;

import java.util.List;

public class ProductDashboardService {

    public List<ProductDashboardDTO> getAllDashboardProducts() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return new ProductDashboardDAO(em).findAllDashboard();
        } finally {
            em.close();
        }
    }

    public String deleteProduct(int productId) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            ProductDashboardDAO dao = new ProductDashboardDAO(em);
            Product product = dao.findByIdEntity(productId);

            if (product == null) {
                tx.rollback();
                throw new RuntimeException("Không tìm thấy sản phẩm với ID = " + productId);
            }

            dao.deleteEntity(product);
            tx.commit();
            return "Sản phẩm đã được xoá thành công";
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public ProductDashboardDTO updateProduct(int id, ProductDashboardDTO rq) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            ProductDashboardDAO dao = new ProductDashboardDAO(em);
            Product p = dao.findByIdEntity(id);
            if (p == null) {
                tx.rollback();
                return null;
            }

            p.setProductName(rq.getProductName());
            p.setWholesalePrice(rq.getWholesalePrice());
            p.setDescription(rq.getDescription());

            Category category = em.find(Category.class, rq.getCategoryId());
            p.setCategory(category);

            dao.updateEntity(p); // merge

            tx.commit();

            return dao.findDashboardById(id); // trả về DTO sau cập nhật
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    public List<ProductDashboardDTO> getRelatedProducts(Long productId, int limit) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return new ProductDashboardDAO(em).findRelatedProducts(productId, limit);
        } finally {
            em.close();
        }
    }


}
