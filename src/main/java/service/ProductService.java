package service;

import dao.ProductDAO;
import entity.Product;
import entity.Category;
import util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class ProductService {
    public List<Product> getAllProducts(String search, Integer categoryId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            ProductDAO dao = new ProductDAO(em);
            if (search != null && !search.isEmpty()) return dao.searchByName(search);
            if (categoryId != null) return dao.findByCategory(categoryId);
            return dao.findAll();
        } finally { em.close(); }
    }

    public Product getProductById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try { return new ProductDAO(em).findById(id); }
        finally { em.close(); }
    }

    public Product createProduct(Product p, int categoryId) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Category c = em.find(Category.class, categoryId);
            p.setCategory(c);
            new ProductDAO(em).create(p);
            tx.commit();
            return p;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally { em.close(); }
    }

    public Product updateProduct(int id, Product newData, int categoryId) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ProductDAO dao = new ProductDAO(em);
            Product p = dao.findById(id);
            if (p == null) return null;
            p.setProductName(newData.getProductName());
            p.setEntryPrice(newData.getEntryPrice());
            p.setRetailPrice(newData.getRetailPrice());
            p.setWholesalePrice(newData.getWholesalePrice());
            p.setDescription(newData.getDescription());
            Category c = em.find(Category.class, categoryId);
            p.setCategory(c);
            dao.update(p);
            tx.commit();
            return p;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally { em.close(); }
    }

    public boolean deleteProduct(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ProductDAO dao = new ProductDAO(em);
            Product p = dao.findById(id);
            if (p == null) return false;
            dao.delete(p);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally { em.close(); }
    }
}
