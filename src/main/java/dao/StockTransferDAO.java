package dao;

import entity.StockTransfer;
import jakarta.persistence.EntityManager;

public class StockTransferDAO extends GenericDAO<StockTransfer> {
    public StockTransferDAO(EntityManager entityManager) {
        super(StockTransfer.class, entityManager);
    }
}
