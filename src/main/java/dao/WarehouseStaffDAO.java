package dao;

import entity.WarehouseStaff;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class WarehouseStaffDAO extends GenericDAO<WarehouseStaff> {
    public WarehouseStaffDAO(EntityManager em) {
        super(WarehouseStaff.class, em);
    }

    public void createAccount(WarehouseStaff warehouseStaff) {
        save(warehouseStaff);
    }

    public WarehouseStaff findByUsername(String username) {
        try {
            String jpql = "SELECT w FROM WarehouseStaff w WHERE w.username = :username";
            TypedQuery<WarehouseStaff> query = em.createQuery(jpql, WarehouseStaff.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}