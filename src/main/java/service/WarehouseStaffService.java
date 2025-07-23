package service;

import dao.WarehouseStaffDAO;
import entity.WarehouseStaff;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

public class WarehouseStaffService {
    public WarehouseStaff getWarehouseStaffById(int warehouseStaffId) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        try {
            WarehouseStaffDAO warehouseStaffDAO = new WarehouseStaffDAO(entityManager);
            return warehouseStaffDAO.findById(warehouseStaffId);
        } finally {
            entityManager.close();
        }
    }
}
