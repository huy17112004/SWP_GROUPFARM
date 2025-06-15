package dao;

import entity.Address;
import jakarta.persistence.EntityManager;

public class AddressDAO extends GenericDAO<Address> {
    public AddressDAO(EntityManager em) {
        super(Address.class, em);

    }
}
