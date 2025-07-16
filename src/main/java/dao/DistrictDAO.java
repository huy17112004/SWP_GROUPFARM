package dao;

import entity.District;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DistrictDAO extends GenericDAO<District> {
    public DistrictDAO(EntityManager em) {
        super(District.class, em);
    }

    public List<District> getDistrictsByProvinceId(int provinceId) {
        return em.createQuery("select d from District d where d.province.id = :provinceId", District.class)
                .setParameter("provinceId", provinceId).getResultList();
    }
}
