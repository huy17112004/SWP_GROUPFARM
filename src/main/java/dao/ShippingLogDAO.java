package dao;

import entity.ShippingLog;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ShippingLogDAO extends GenericDAO<ShippingLog> {
    public ShippingLogDAO(EntityManager em) {
        super(ShippingLog.class, em);
    }

    public List<ShippingLog> findByShipperId(Integer shipperId) {
        return em.createQuery(
                "SELECT s FROM ShippingLog s WHERE s.shipper.id = :shipperId", ShippingLog.class)
                .setParameter("shipperId", shipperId).getResultList();
    }
}
