package dao;

import entity.Warehouse;
import jakarta.persistence.EntityManager;

public class WarehouseDAO extends GenericDAO<Warehouse> {
    public WarehouseDAO(EntityManager em) {
        super(Warehouse.class, em);
    }
}
