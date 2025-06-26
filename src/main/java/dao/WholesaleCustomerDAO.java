package dao;

import entity.Account;
import entity.WholesaleCustomer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import util.JpaUtil;

public class WholesaleCustomerDAO extends GenericDAO<WholesaleCustomer> {
    public WholesaleCustomerDAO(EntityManager em) {
        super(WholesaleCustomer.class, em);
    }

    public WholesaleCustomer findByUsernameAndPassword(String username, String password) {
        try {
            String jpql = "SELECT a FROM WholesaleCustomer a WHERE a.username = :username AND a.password = :password";
            TypedQuery<WholesaleCustomer> query = em.createQuery(jpql, WholesaleCustomer.class);
            query.setParameter("username", username);
            query.setParameter("password", password);

            return query.getSingleResult(); // Nếu tìm thấy
        } catch (NoResultException e) {
            return null; // Không tìm thấy
        }
    }

    public WholesaleCustomer findByUsername(String username) {
        try {
            String jpql = "SELECT a FROM WholesaleCustomer a WHERE a.username = :username";
            TypedQuery<WholesaleCustomer> query = em.createQuery(jpql, WholesaleCustomer.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public WholesaleCustomer findByEmail(String email) {
        try {
            String jpql = "SELECT a FROM WholesaleCustomer a WHERE a.email = :email";
            TypedQuery<WholesaleCustomer> query = em.createQuery(jpql, WholesaleCustomer.class);

            query.setParameter("email", email);

            return query.getSingleResult(); // Nếu tìm thấy
        } catch (NoResultException e) {
            return null; // Không tìm thấy
        }
    }

    public void createAccount(WholesaleCustomer customer) {
        try {
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to create account: " + e.getMessage(), e);
        }
    }
}