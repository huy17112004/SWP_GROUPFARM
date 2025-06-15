package dao;

import entity.CustomerAddress;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

public class CustomerAddressDAO extends GenericDAO<CustomerAddress> {
    public CustomerAddressDAO(EntityManager em) {
        super(CustomerAddress.class, em);
    }
}
