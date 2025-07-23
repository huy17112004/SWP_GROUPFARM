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

    public List<WholesaleOrder> findAllByCustomerIdWithItems(int customerId) {
        TypedQuery<WholesaleOrder> q = em.createQuery(
                "SELECT DISTINCT o FROM WholesaleOrder o " +
                        " LEFT JOIN FETCH o.items i" +
                        " LEFT JOIN FETCH i.product p" +
                        " WHERE o.customer.id = :customerId ORDER BY o.createdAt DESC",
                WholesaleOrder.class);
        q.setParameter("customerId", customerId);
        return q.getResultList();
    }

    public List<WholesaleOrder> findAllBySellerIdWithItems(int sellerId) {
        TypedQuery<WholesaleOrder> q = em.createQuery(
                "SELECT DISTINCT o FROM WholesaleOrder o " +
                        " LEFT JOIN FETCH o.items i" +
                        " LEFT JOIN FETCH i.product p" +
                        " LEFT JOIN FETCH o.customer c" +
                        " WHERE o.seller.id = :sellerId ORDER BY o.createdAt DESC",
                WholesaleOrder.class);
        q.setParameter("sellerId", sellerId);
        return q.getResultList();
    }

    public List<WholesaleOrder> findAllByStatusWithItems(String status) {
        TypedQuery<WholesaleOrder> q = em.createQuery(
                "SELECT DISTINCT o FROM WholesaleOrder o " +
                        " LEFT JOIN FETCH o.items i" +
                        " LEFT JOIN FETCH i.product p" +
                        " LEFT JOIN FETCH o.customer c" +
                        " WHERE o.status = :status ORDER BY o.createdAt DESC",
                WholesaleOrder.class);
        q.setParameter("status", status);
        return q.getResultList();
    }

    public List<WholesaleOrder> findAllByStatusAndSourceWarehouse(int sourceWarehouseId, String status) {
        TypedQuery<WholesaleOrder> q = em.createQuery(
                "SELECT DISTINCT o FROM WholesaleOrder o " +
                        " LEFT JOIN FETCH o.items i" +
                        " LEFT JOIN FETCH i.product p" +
                        " LEFT JOIN FETCH o.customer c" +
                        " WHERE o.sourceWarehouse.id = :sourceWarehouseId " +
                        " AND o.status = :status ORDER BY o.createdAt DESC",
                WholesaleOrder.class);
        q.setParameter("status", status);
        q.setParameter("sourceWarehouseId", sourceWarehouseId);
        return q.getResultList();
    }

    public List<WholesaleOrder> findAllByStatusAndShipper(int shipperId, String status) {
        TypedQuery<WholesaleOrder> q = em.createQuery(
                "SELECT DISTINCT o FROM WholesaleOrder o " +
                        " LEFT JOIN FETCH o.items i" +
                        " LEFT JOIN FETCH i.product p" +
                        "LEFT JOIN FETCH o.shippingLog s" +
                        " LEFT JOIN FETCH o.customer c" +
                        " WHERE s.shipper.id = :shipperId " +
                        " AND o.status = :status ORDER BY o.createdAt DESC",
                WholesaleOrder.class);
        q.setParameter("status", status);
        q.setParameter("shipperId", shipperId);
        return q.getResultList();
    }
}
