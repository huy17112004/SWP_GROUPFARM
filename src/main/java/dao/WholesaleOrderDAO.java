package dao;

import entity.WholesaleOrder;
import jakarta.persistence.EntityManager;

public class WholesaleOrderDAO extends GenericDAO<WholesaleOrder> {
    public WholesaleOrderDAO(EntityManager entityManager) {
        super(WholesaleOrder.class, entityManager);
    }


}
