package dao;

import entity.ShippingRequirement;
import entity.StockLot;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StockLotDAO {
    private final EntityManager em;
    public StockLotDAO(EntityManager em) { this.em = em; }

    public StockLot findById(int id) {
        return em.find(StockLot.class, id);
    }

    public List<StockLot> findAll() {
        TypedQuery<StockLot> q = em.createQuery(
                "SELECT s FROM StockLot s JOIN FETCH s.product p ORDER BY s.importDate DESC", StockLot.class);
        return q.getResultList();
    }

    public List<StockLot> findByWarehouse(int warehouseId) {
        TypedQuery<StockLot> q = em.createQuery(
                "SELECT s FROM StockLot s JOIN FETCH s.product p WHERE s.warehouse.id = :wid ORDER BY s.importDate DESC", StockLot.class);
        q.setParameter("wid", warehouseId);
        return q.getResultList();
    }

    public void create(StockLot stockLot) {
        em.persist(stockLot);
    }

    public StockLot update(StockLot stockLot) {
        return em.merge(stockLot);
    }

    public void delete(StockLot stockLot) {
        StockLot managed = stockLot;
        if (!em.contains(stockLot)) managed = em.merge(stockLot);
        em.remove(managed);
    }

    public List<StockLot> findEligibleStockLotsJava(int productId, Date deliveryDate) {
        ShippingRequirement req = em.find(ShippingRequirement.class, productId);
        int minDays = req != null ? req.getMinExpiryDaysRequired() : 0;

        List<StockLot> allLots = em.createQuery(
                        "SELECT s FROM StockLot s WHERE s.product.id = :pid", StockLot.class)
                .setParameter("pid", productId)
                .getResultList();

        return allLots.stream()
                .filter(s -> {
                    // s.expiredDate - minDays > deliveryDate
                    long timeOk = s.getExpiredDate().toInstant().minusSeconds(minDays * 24 * 60 * 60).toEpochMilli();
                    return timeOk > deliveryDate.toInstant().toEpochMilli();
                })
                .collect(Collectors.toList());
    }
}
