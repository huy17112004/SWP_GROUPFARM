package dao;

import entity.Cart;
import jakarta.persistence.EntityManager;

import java.io.Serializable;
import java.util.List;

public class CartDAO extends GenericDAO<Cart> {
    public CartDAO(EntityManager entityManager) {
        super(Cart.class, entityManager);
    }

    public List<Cart> findAllByCustomerId(int customerId) {
            List<Cart> list = em.createQuery(
                            "SELECT DISTINCT c FROM Cart c " +
                                    "LEFT JOIN FETCH c.product " +
                                    "WHERE c.customer.id = :customerId", Cart.class)
                    .setParameter("customerId", customerId)
                    .getResultList();
            return list;
    }

    public void deleteByCustomerId(int customerId) {
        em.createQuery("DELETE FROM Cart c WHERE c.customer.id = :customerId")
                .setParameter("customerId", customerId)
                .executeUpdate();
    }
}
