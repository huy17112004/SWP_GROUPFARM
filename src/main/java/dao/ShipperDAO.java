package dao;

import entity.Shipper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class ShipperDAO extends GenericDAO<Shipper> {
    public ShipperDAO(EntityManager em) {
        super(Shipper.class, em);
    }

    public void createAccount(Shipper shipper) {
        save(shipper);
    }

    public Shipper findByUsername(String username) {
        try {
            String jpql = "SELECT s FROM Shipper s WHERE s.username = :username";
            TypedQuery<Shipper> query = em.createQuery(jpql, Shipper.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}