package dao;

import entity.Product;
import entity.WholesaleCustomer;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

import java.util.List;

public class ProductDAO extends GenericDAO<Product> {
    public ProductDAO() {
        super(Product.class);
    }

    @Override
    public List<Product> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            List<Product> list = em.createQuery(
                    "SELECT DISTINCT p FROM Product p " +
                    "LEFT JOIN FETCH p.images " +
                    "LEFT JOIN FETCH p.category", Product.class).getResultList();
            return list;
        } finally {
            em.close();
        }
    }
}
