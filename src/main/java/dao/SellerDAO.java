package dao;

import entity.Admin;
import entity.Seller;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDAO extends GenericDAO<Seller> {
    public SellerDAO(EntityManager em) {
        super(Seller.class, em);
    }

    public Seller findByUsername(String username) {
        try {
            String jpql = "SELECT s FROM Seller s WHERE s.username = :username";
            TypedQuery<Seller> query = em.createQuery(jpql, Seller.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Seller findByAccountId(Integer accountId) {
        return em.find(Seller.class, accountId);
    }

    public List<Seller> getActiveSellers() {
        String jpql = "SELECT s FROM Seller s WHERE s.status = 'ACTIVE'";
        return em.createQuery(jpql, Seller.class).getResultList();
    }

}