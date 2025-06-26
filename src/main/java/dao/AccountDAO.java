package dao;

import entity.Account;
import jakarta.persistence.EntityManager;

public class AccountDAO extends GenericDAO<Account> {
    public AccountDAO(EntityManager em) {
        super(Account.class, em);
    }
}
