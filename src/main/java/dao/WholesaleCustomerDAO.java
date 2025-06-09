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

    public WholesaleCustomer checkLogin(String username, String password) {
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
}
