package dao;

import entity.Ward;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

import java.util.List;

public class WardDAO extends GenericDAO<Ward> {
    public WardDAO(EntityManager em) {
        super(Ward.class, em);
    }

    public List<Ward> findWardsByDistrictId(int districtId) {
        return em.createQuery("SELECT w FROM Ward w WHERE w.district.id = :districtId", Ward.class)
                .setParameter("districtId", districtId).getResultList();
    }
}
