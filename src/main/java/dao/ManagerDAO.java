package dao;

import entity.Manager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class ManagerDAO extends GenericDAO<Manager> {
    public ManagerDAO(EntityManager em) {
        super(Manager.class, em);
    }

    public Manager findByUsername(String username) {
        try {
            String jpql = "SELECT m FROM Manager m WHERE m.username = :username";
            TypedQuery<Manager> query = em.createQuery(jpql, Manager.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}