package dao;

import entity.WholesaleOrder;
import jakarta.persistence.EntityManager;

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
}
