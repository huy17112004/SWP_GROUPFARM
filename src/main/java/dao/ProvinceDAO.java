package dao;

import entity.Province;
import jakarta.persistence.EntityManager;

public class ProvinceDAO extends GenericDAO<Province>{
    public ProvinceDAO(EntityManager em) {
        super(Province.class, em);
    }
}
