package dao;

import entity.Manager;
import entity.Seller;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class ManagerDAO extends GenericDAO<Manager> {
    public ManagerDAO(EntityManager em) {
        super(Manager.class, em);
    }

    public void createAccount(Manager manager) {
        save(manager);
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
    public Manager findByAccountId(Integer accountId) {
        return em.find(Manager.class, accountId);
    }
}