package dao;

import entity.Account;
import entity.WholesaleCustomer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import util.JpaUtil;

public class WholesaleCustomerDAO extends GenericDAO<WholesaleCustomer> {
    public WholesaleCustomerDAO() {
        super(WholesaleCustomer.class);
    }

    public WholesaleCustomer findByUsernameAndPassword(String username, String password) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            String jpql = "SELECT a FROM WholesaleCustomer a WHERE a.username = :username AND a.password = :password";
            TypedQuery<WholesaleCustomer> query = em.createQuery(jpql, WholesaleCustomer.class);
            query.setParameter("username", username);
            query.setParameter("password", password);

            return query.getSingleResult(); // Nếu tìm thấy
        } catch (NoResultException e) {
            return null; // Không tìm thấy
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

    public WholesaleCustomer findByEmail(String email) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            String jpql = "SELECT a FROM WholesaleCustomer a WHERE a.email = :email";
            TypedQuery<WholesaleCustomer> query = em.createQuery(jpql, WholesaleCustomer.class);

            query.setParameter("email", email);

            return query.getSingleResult(); // Nếu tìm thấy
        } catch (NoResultException e) {
            return null; // Không tìm thấy
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

//    public WholesaleCustomer findByUsername(String username) {
//        EntityManager em = JpaUtil.getEntityManager();
//        try {
//            String jpql = "SELECT a FROM WholesaleCustomer a WHERE a.username = :username";
//            return em.createQuery(jpql, WholesaleCustomer.class)
//                    .setParameter("username", username)
//                    .getSingleResult();
//        } catch (NoResultException e) {
//            return null;
//        } finally {
//            em.close();
//        }
//    }


    public void createAccount(WholesaleCustomer customer) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to create account: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}