package dao;

import entity.Ward;
import jakarta.persistence.EntityManager;

public class WardDAO extends GenericDAO<Ward> {
    public WardDAO(EntityManager em) {
        super(Ward.class, em);
    }
}
