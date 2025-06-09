package dao;

import entity.WholesaleCustomer;
import org.hibernate.Session;
import util.HibernateUtil;

import java.io.Serializable;

public class WholesaleCustomerDAO extends GenericDAO<WholesaleCustomer> {
    public WholesaleCustomerDAO() {
        super(WholesaleCustomer.class);
    }

<<<<<<< Updated upstream
    public WholesaleCustomer findByCompanyName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM WholesaleCustomer WHERE companyName = :companyName";
            return session.createQuery(hql, WholesaleCustomer.class)
                    .setParameter("companyName", name)
                    .uniqueResult();
=======
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
>>>>>>> Stashed changes
        }
    }
}
