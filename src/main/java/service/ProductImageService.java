package service;

import dao.ProductImageDAO;
import entity.ProductImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import util.JpaUtil;

public class ProductImageService {
    
    public void save(ProductImage productImage) {
        EntityManager em = JpaUtil.getEntityManager();
        ProductImageDAO productImageDAO = new ProductImageDAO(em);
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            productImageDAO.save(productImage);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }
    
    // Method để sử dụng trong transaction hiện tại
    public void saveInTransaction(ProductImage productImage, EntityManager em) {
        ProductImageDAO productImageDAO = new ProductImageDAO(em);
        productImageDAO.save(productImage);
    }
}
