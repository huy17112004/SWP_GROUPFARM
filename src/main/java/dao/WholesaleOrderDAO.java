package dao;

import entity.WholesaleOrder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WholesaleOrderDAO extends GenericDAO<WholesaleOrder> {
    public WholesaleOrderDAO(EntityManager entityManager) {
        super(WholesaleOrder.class, entityManager);
    }

    public Map<Integer, Long> getNegotiatingCountBySeller() {
        String jpql = "SELECT o.seller.id, COUNT(o) " +
                "FROM WholesaleOrder o " +
                "WHERE o.status = 'NEGOTIATING' " +
                "GROUP BY o.seller.id";

        List<Object[]> results = em.createQuery(jpql, Object[].class).getResultList();

        Map<Integer, Long> map = new HashMap<>();
        for (Object[] row : results) {
            Integer sellerId = (Integer) row[0];
            Long count = (Long) row[1];
            map.put(sellerId, count);
        }
        return map;
    }

    public WholesaleOrder findByIdWithItems(int orderId) {
        TypedQuery<WholesaleOrder> q = em.createQuery(
                "SELECT o FROM WholesaleOrder o " +
                        " LEFT JOIN FETCH o.items i" +
                        " LEFT JOIN FETCH i.product p" +
                        " WHERE o.id = :id",
                WholesaleOrder.class);
        q.setParameter("id", orderId);
        return q.getSingleResult();
    }

    public List<WholesaleOrder> findPendingOrders() {
        String jpql = "SELECT o FROM WholesaleOrder o WHERE o.status = 'PENDING'";
        return em.createQuery(jpql, WholesaleOrder.class).getResultList();
    }

    public List<Object[]> findPendingOrderBasicInfo() {
        String jpql = "SELECT o.id, a.street, c.contactPerson, c.phone, o.note " +
                "FROM WholesaleOrder o " +
                "JOIN o.deliveryAddress a " +
                "JOIN o.customer c " +
                "WHERE o.status = 'PENDING'";
        return em.createQuery(jpql, Object[].class).getResultList();
    }

    public List<Object[]> findDeliveringOrderBasicInfo() {
        String jpql = "SELECT o.id, a.street, c.contactPerson, c.phone, o.status " +
                "FROM WholesaleOrder o " +
                "JOIN o.deliveryAddress a " +
                "JOIN o.customer c " +
                "WHERE o.status NOT IN ('PENDING', 'COMPLETED', 'CANCELLED', 'CREATED')";
        return em.createQuery(jpql, Object[].class).getResultList();
    }

    public List<Object[]> findCompletedOrderBasicInfo() {
        // Lấy các đơn hàng đã kết thúc và log vận chuyển cuối cùng của chúng
        String jpql = "SELECT o.id, a.street, c.contactPerson, c.phone, o.status, sl.timestamp, sl.note " +
                "FROM ShippingLog sl " +
                "JOIN sl.order o " +
                "JOIN o.deliveryAddress a " +
                "JOIN o.customer c " +
                "WHERE o.status IN ('COMPLETED', 'RETURNED', 'CANCELLED') " +
                "AND sl.id = (SELECT MAX(sl2.id) FROM ShippingLog sl2 WHERE sl2.order.id = o.id)";
        return em.createQuery(jpql, Object[].class).getResultList();
    }
}
