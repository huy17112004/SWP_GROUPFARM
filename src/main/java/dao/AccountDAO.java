package dao;

import entity.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class AccountDAO extends GenericDAO<Account> {
    public AccountDAO(EntityManager em) {
        super(Account.class, em);
    }
    public Account findByUsername(String username) {
        try {
            return em.createQuery(
                            "SELECT a FROM Account a WHERE a.username = :u", Account.class)
                    .setParameter("u", username)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
