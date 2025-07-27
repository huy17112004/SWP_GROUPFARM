package service;

import dao.WardDAO;
import dao.WarehouseDAO;
import dto.WarehouseCreateDTO;
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
    public void createFromDTO(WarehouseCreateDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            WarehouseDAO warehouseDAO = new WarehouseDAO(em);
            // 1. Kiểm tra trùng
            long existing = warehouseDAO.countByNameOrPhone(dto.getWarehouseName(), dto.getWarehousePhone());
            if (existing > 0) {
                throw new IllegalStateException("Tên kho hoặc số điện thoại đã tồn tại");
            }

            // 1. Tạo Address
            Address address = new Address();
            address.setStreet(dto.getStreet());
            address.setLatitude((float)dto.getLatitude());
            address.setLongitude((float)dto.getLongitude());
            WardDAO wardDAO = new WardDAO(em);
            address.setWard(wardDAO.findById(dto.getWardId()));
            em.persist(address);

            // 2. Tạo Warehouse
            Warehouse warehouse = new Warehouse();
            warehouse.setWarehouseName(dto.getWarehouseName());
            warehouse.setWarehousePhone(dto.getWarehousePhone());
            warehouse.setAddress(address);

            // 4. Lưu Warehouse
            warehouseDAO.save(warehouse);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error creating warehouse: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    public void updateWarehouse(int id, WarehouseCreateDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // 1. Lấy Warehouse hiện tại
            WarehouseDAO warehouseDAO = new WarehouseDAO(em);
            Warehouse warehouse = warehouseDAO.findById(id);
            if (warehouse == null) {
                throw new IllegalStateException("Warehouse không tồn tại với id = " + id);
            }

            // 2. Cập nhật dữ liệu chính
            warehouse.setWarehouseName(dto.getWarehouseName());
            warehouse.setWarehousePhone(dto.getWarehousePhone());

            // 4. Cập nhật Address
            Address address = warehouse.getAddress();
            address.setStreet(dto.getStreet());
            address.setLatitude((float) dto.getLatitude());
            address.setLongitude((float) dto.getLongitude());

            Ward ward = new WardDAO(em).findById(dto.getWardId());
            if (ward == null) {
                throw new IllegalStateException("Ward không tồn tại với id = " + dto.getWardId());
            }
            address.setWard(ward);

            // 5. Persist changes (JPA sẽ tự động update vì entity đang trong persistence context)
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    /** Xóa kho theo ID */
    public void deleteWarehouse(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            WarehouseDAO warehouseDAO = new WarehouseDAO(em);
            Warehouse warehouse = warehouseDAO.findById(id);
            if (warehouse == null) {
                throw new IllegalStateException("Warehouse không tồn tại với id = " + id);
            }

            // Nếu có cascade = REMOVE giữa Warehouse và Address, Address sẽ bị xóa tự động.
            warehouseDAO.delete(warehouse);

            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}