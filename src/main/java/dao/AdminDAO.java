package dao;

import entity.Admin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class AdminDAO extends GenericDAO<Admin> {
    public AdminDAO(EntityManager em) {
        super(Admin.class, em);
    }

    public void createAccount(Admin admin) {
        save(admin);
    }

    public Admin findByUsername(String username) {
        try {
            String jpql = "SELECT a FROM Admin a WHERE a.username = :username";
            TypedQuery<Admin> query = em.createQuery(jpql, Admin.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}