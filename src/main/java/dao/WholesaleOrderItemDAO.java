package dao;

import entity.WholesaleOrderItem;
import jakarta.persistence.EntityManager;

public class WholesaleOrderItemDAO extends GenericDAO<WholesaleOrderItem> {
    public WholesaleOrderItemDAO(EntityManager em) {
        super(WholesaleOrderItem.class, em);
    }
}
