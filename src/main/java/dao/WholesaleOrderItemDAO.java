package dao;

import entity.Cart;
import entity.WholesaleOrderItem;
import jakarta.persistence.EntityManager;

import java.util.List;

public class WholesaleOrderItemDAO extends GenericDAO<WholesaleOrderItem> {
    public WholesaleOrderItemDAO(EntityManager em) {
        super(WholesaleOrderItem.class, em);
    }
}
