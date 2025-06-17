package dao;

import entity.WarehouseManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class WarehouseManagerDAO extends GenericDAO<WarehouseManager> {
    public WarehouseManagerDAO(EntityManager em) {
        super(WarehouseManager.class, em);
    }

    public WarehouseManager findByUsername(String username) {
        try {
            String jpql = "SELECT w FROM WarehouseManager w WHERE w.username = :username";
            TypedQuery<WarehouseManager> query = em.createQuery(jpql, WarehouseManager.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}