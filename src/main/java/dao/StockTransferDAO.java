package dao;

import entity.StockTransfer;
import entity.StockTransferItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class StockTransferDAO extends GenericDAO<StockTransfer> {
    public StockTransferDAO(EntityManager entityManager) {
        super(StockTransfer.class, entityManager);
    }

    public List<StockTransfer> findBySourceWarehouseAndStatuses(int sourceWarehouseId, List<String> statuses) {
        String jpql = "SELECT s FROM StockTransfer s WHERE s.sourceWarehouse.id = :sourceWarehouseId";
        if (statuses != null && !statuses.isEmpty()) {
            jpql += " AND s.status IN :statuses";
        }

        TypedQuery<StockTransfer> query = em.createQuery(jpql, StockTransfer.class);
        query.setParameter("sourceWarehouseId", sourceWarehouseId);

        if (statuses != null && !statuses.isEmpty()) {
            query.setParameter("statuses", statuses);
        }

        return query.getResultList();
    }

    public List<StockTransfer> findByDestinationWarehouseAndStatuses(int destinationWarehouseId, List<String> statuses) {
        String jpql = "SELECT s FROM StockTransfer s WHERE s.destinationWarehouse.id = :destinationWarehouseId";
        if (statuses != null && !statuses.isEmpty()) {
            jpql += " AND s.status IN :statuses";
        }

        TypedQuery<StockTransfer> query = em.createQuery(jpql, StockTransfer.class);
        query.setParameter("destinationWarehouseId", destinationWarehouseId);
        if (statuses != null && !statuses.isEmpty()) {
            query.setParameter("statuses", statuses);
        }

        return query.getResultList();
    }
}
