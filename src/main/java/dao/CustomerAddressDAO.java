package dao;

import entity.CustomerAddress;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

import java.util.List;

public class CustomerAddressDAO extends GenericDAO<CustomerAddress> {
    public CustomerAddressDAO(EntityManager em) {
        super(CustomerAddress.class, em);
    }

    public List<CustomerAddress> findByCustomer(int customerId) {
        String jpql = "SELECT ca FROM CustomerAddress ca WHERE ca.wholesaleCustomer.id = :customerId";
        return em.createQuery(jpql, CustomerAddress.class)
                .setParameter("customerId", customerId)
                .getResultList();
    }
}
