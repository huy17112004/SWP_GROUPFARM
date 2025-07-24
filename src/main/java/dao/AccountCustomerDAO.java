package dao;

import entity.WholesaleCustomer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import util.JpaUtil;

public class AccountCustomerDAO {
    public WholesaleCustomer findByEmail(String email) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<WholesaleCustomer> q = em.createQuery(
                    "SELECT c FROM WholesaleCustomer c WHERE c.email = :e", WholesaleCustomer.class);
            q.setParameter("e", email);
            return q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public void createOrUpdate(WholesaleCustomer c) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(c);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
    public WholesaleCustomer findByUsername(String username) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            String jpql = "SELECT a FROM WholesaleCustomer a WHERE a.username = :username";
            TypedQuery<WholesaleCustomer> query = em.createQuery(jpql, WholesaleCustomer.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
