package dao;

import entity.Product;
import jakarta.persistence.EntityManager;

public class ProductCreateDAO {
    private final EntityManager em;
    public ProductCreateDAO(EntityManager em) { this.em = em; }

    public void create(Product p) {
        em.persist(p);
    }
}