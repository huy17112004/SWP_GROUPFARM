package dao;

import entity.Saler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class SalerDAO extends GenericDAO<Saler> {
    public SalerDAO(EntityManager em) {
        super(Saler.class, em);
    }

    public void createAccount(Saler saler) {
        save(saler);
    }

    public Saler findByUsername(String username) {
        try {
            String jpql = "SELECT s FROM Saler s WHERE s.username = :username";
            TypedQuery<Saler> query = em.createQuery(jpql, Saler.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}