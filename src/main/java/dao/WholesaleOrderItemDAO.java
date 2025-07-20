package dao;

import entity.Cart;
import entity.WholesaleOrderItem;
import jakarta.persistence.EntityManager;

import java.util.List;

public class WholesaleOrderItemDAO extends GenericDAO<WholesaleOrderItem> {
    public WholesaleOrderItemDAO(EntityManager em) {
        super(WholesaleOrderItem.class, em);
    }

    public List<WholesaleOrderItem> findOrderItemsByOrderId(int orderId) {
        return em.createQuery(
                        "SELECT oi FROM WholesaleOrderItem oi " +
                                "JOIN FETCH oi.product p " +
                                "JOIN FETCH oi.order o " +
                                "WHERE o.id = :orderId",
                        WholesaleOrderItem.class
                ).setParameter("orderId", orderId)
                .getResultList();
    }
}
