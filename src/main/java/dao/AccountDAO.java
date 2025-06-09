package dao;

import entity.Account;
import entity.WholesaleCustomer;
import jakarta.persistence.*;
import util.JpaUtil;

public class AccountDAO extends GenericDAO<Account> {
    public AccountDAO() {
        super(Account.class);
    }

    public Account checkLogin(String username, String password) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            String jpql = "SELECT a FROM Account a WHERE a.username = :username AND a.password = :password";
            TypedQuery<Account> query = em.createQuery(jpql, Account.class);
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
