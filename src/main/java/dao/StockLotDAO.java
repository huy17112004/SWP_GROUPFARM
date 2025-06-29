package dao;

import entity.ShippingRequirement;
import entity.StockLot;
import jakarta.persistence.EntityManager;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StockLotDAO extends GenericDAO<StockLot> {
    public StockLotDAO(EntityManager em) {
        super(StockLot.class, em);
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
