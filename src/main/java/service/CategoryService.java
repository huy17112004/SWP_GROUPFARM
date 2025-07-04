package service;

import dao.CategoryDAO;
import entity.Category;
import util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class CategoryService {
    public List<Category> getAllCategories() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            CategoryDAO dao = new CategoryDAO(em);
            return dao.findAll();
        } finally {
            em.close();
        }
    }

    public Category getCategoryById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            CategoryDAO dao = new CategoryDAO(em);
            return dao.findById(id);
        } finally {
            em.close();
        }
    }

    public Category createCategory(String name) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Category c = new Category();
            c.setCategoryName(name);
            CategoryDAO dao = new CategoryDAO(em);
            dao.create(c);
            tx.commit();
            return c;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Category updateCategory(int id, String name) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CategoryDAO dao = new CategoryDAO(em);
            Category c = dao.findById(id);
            if (c == null) {
                return null;
            }
            c.setCategoryName(name);
            dao.update(c);
            tx.commit();
            return c;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public boolean deleteCategory(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CategoryDAO dao = new CategoryDAO(em);
            Category c = dao.findById(id);
            if (c == null) {
                return false;
            }
            dao.delete(c);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}