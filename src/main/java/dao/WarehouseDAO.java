package dao;

import entity.Warehouse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class WarehouseDAO extends GenericDAO<Warehouse> {
    public WarehouseDAO(EntityManager em) {
        super(Warehouse.class, em);
    }
    public long countByNameOrPhone(String name, String phone) {
        String jpql = "SELECT COUNT(w) FROM Warehouse w WHERE w.warehouseName = :name OR w.warehousePhone = :phone";
        TypedQuery<Long> q = em.createQuery(jpql, Long.class);
        q.setParameter("name", name);
        q.setParameter("phone", phone);
        return q.getSingleResult();
    }
}