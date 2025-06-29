package service;

import dao.WardDAO;
import dao.WarehouseDAO;
import dto.WarehouseRequestDTO;
import entity.Address;
import entity.Ward;
import entity.Warehouse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import util.JpaUtil;

public class WarehouseService {

    public void addWarehouse(WarehouseRequestDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            WarehouseDAO warehouseDAO = new WarehouseDAO(em);
            WardDAO wardDAO = new WardDAO(em);
            // 1. Tạo Address entity từ dto
            Address address = new Address();
            address.setStreet(dto.getStreet());
            address.setLatitude(dto.getLatitude());
            address.setLongitude(dto.getLongitude());
            address.setWard(wardDAO.findById(dto.getWardId()));

            // 2. Tạo Warehouse entity từ dto
            Warehouse warehouse = new Warehouse();
            warehouse.setWarehouseName(dto.getWarehouseName());
            warehouse.setWarehousePhone(dto.getWarehousePhone());
            warehouse.setAddress(address);

            // 3. Lưu
            warehouseDAO.save(warehouse);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }

    }

}
