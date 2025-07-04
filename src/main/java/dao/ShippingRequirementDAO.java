package dao;

import entity.Manager;
import entity.ShippingRequirement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class ShippingRequirementDAO extends GenericDAO<ShippingRequirement> {
    public ShippingRequirementDAO(EntityManager em) {
        super(ShippingRequirement.class, em);
    }

    public ShippingRequirement findByProductId(int productId) {
        try {
            String jpql = "SELECT s FROM ShippingRequirement s WHERE s.productId = :productId";
            TypedQuery<ShippingRequirement> query = em.createQuery(jpql, ShippingRequirement.class);
            query.setParameter("productId", productId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
