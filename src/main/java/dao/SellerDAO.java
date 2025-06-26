package dao;

import entity.Seller;
import jakarta.persistence.EntityManager;

import java.util.List;

public class SellerDAO extends GenericDAO<Seller> {
    public SellerDAO(EntityManager em) {
        super(Seller.class, em);
    }
    public List<Seller> getActiveSellers() {
        return em.createQuery(
                        "SELECT s FROM Seller s WHERE s.status = :status", Seller.class)
                .setParameter("status", "Active")
                .getResultList();
    }
}
