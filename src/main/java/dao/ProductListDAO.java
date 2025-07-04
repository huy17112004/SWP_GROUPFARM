package dao;

import entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProductListDAO {
    private final EntityManager em;
    public ProductListDAO(EntityManager em) { this.em = em; }

    public Product findById(int id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAll() {
        TypedQuery<Product> q = em.createQuery(
                "SELECT p FROM Product p LEFT JOIN FETCH p.category ORDER BY p.productName", Product.class
        );
        return q.getResultList();
    }

    public List<Product> searchByName(String keyword) {
        TypedQuery<Product> q = em.createQuery(
                "SELECT p FROM Product p LEFT JOIN FETCH p.category " +
                        "WHERE LOWER(p.productName) LIKE :kw ORDER BY p.productName", Product.class
        );
        q.setParameter("kw", "%" + keyword.toLowerCase() + "%");
        return q.getResultList();
    }

    public List<Product> findByCategory(int categoryId) {
        TypedQuery<Product> q = em.createQuery(
                "SELECT p FROM Product p LEFT JOIN FETCH p.category " +
                        "WHERE p.category.id = :cid ORDER BY p.productName", Product.class
        );
        q.setParameter("cid", categoryId);
        return q.getResultList();
    }

    public void create(Product product) {
        em.persist(product);
    }

    public Product update(Product product) {
        return em.merge(product);
    }

    public void delete(Product product) {
        Product managed = product;
        if (!em.contains(product)) managed = em.merge(product);
        em.remove(managed);
    }
}
