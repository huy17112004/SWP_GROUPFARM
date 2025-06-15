package dao;

import entity.District;
import jakarta.persistence.EntityManager;

public class DistrictDAO extends GenericDAO<District> {
    public DistrictDAO(EntityManager em) {
        super(District.class, em);
    }
}
