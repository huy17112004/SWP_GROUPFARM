package service;

import dao.StockLotDAO;
import dto.InventoryResponseDTO;
import dto.StockLotRequestDTO;
import dto.StockLotResponseDTO;
import entity.Product;
import entity.StockLot;
import util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for StockLot operations, handling conversion between LocalDate and Date.
 */
public class StockLotService {
    /**
     * Get all stock lots as DTOs.
     */
    public List<StockLotResponseDTO> getAllStockLots() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StockLotDAO dao = new StockLotDAO(em);
            return dao.findAll()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    /**
     * Get a single stock lot by ID.
     */
    public StockLotResponseDTO getStockLotById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StockLot s = new StockLotDAO(em).findById(id);
            return (s != null) ? toDTO(s) : null;
        } finally {
            em.close();
        }
    }

    /**
     * Create a new stock lot from request DTO, converting LocalDate to Date.
     */
    public StockLotResponseDTO createStockLot(StockLotRequestDTO req) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            StockLot s = new StockLot();
            Product p = em.getReference(Product.class, req.getProductId());
            s.setProduct(p);
            s.setWarehouse(em.getReference(entity.Warehouse.class, req.getWarehouseId()));
            s.setQuantity(req.getQuantity());
            // Convert LocalDate to java.util.Date
            Date importDate = Date.from(req.getImportDate()
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant());
            Date expiredDate = Date.from(req.getExpiredDate()
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant());
            s.setImportDate(importDate);
            s.setExpiredDate(expiredDate);

            new StockLotDAO(em).save(s);
            tx.commit();
            return toDTO(s);
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Update an existing stock lot, converting LocalDate to Date.
     */
    public StockLotResponseDTO updateStockLot(int id, StockLotRequestDTO req) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            StockLotDAO dao = new StockLotDAO(em);
            StockLot s = dao.findById(id);
            if (s == null) {
                return null;
            }
            s.setProduct(em.getReference(Product.class, req.getProductId()));
            s.setWarehouse(em.getReference(entity.Warehouse.class, req.getWarehouseId()));
            s.setQuantity(req.getQuantity());
            // Convert LocalDate to java.util.Date
            Date importDate = Date.from(req.getImportDate()
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant());
            Date expiredDate = Date.from(req.getExpiredDate()
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant());
            s.setImportDate(importDate);
            s.setExpiredDate(expiredDate);

            dao.update(s);
            tx.commit();
            return toDTO(s);
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Delete a stock lot by ID.
     */
    public boolean deleteStockLot(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            StockLotDAO dao = new StockLotDAO(em);
            StockLot s = dao.findById(id);
            if (s == null) {
                return false;
            }
            dao.delete(s);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Calculate inventory by warehouse: sum quantities grouped by product.
     */
    public List<InventoryResponseDTO> getInventoryByWarehouse(int warehouseId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT s.product.id, s.product.productName, SUM(s.quantity) " +
                    "FROM StockLot s WHERE s.warehouse.id = :wid " +
                    "GROUP BY s.product.id, s.product.productName";
            List<Object[]> rows = em.createQuery(jpql, Object[].class)
                    .setParameter("wid", warehouseId)
                    .getResultList();
            return rows.stream().map(r -> new InventoryResponseDTO(
                    (int) r[0], (String) r[1], ((Number) r[2]).intValue()
            )).collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    /**
     * Convert entity to response DTO, converting Date to LocalDate.
     */
    private StockLotResponseDTO toDTO(StockLot s) {
        LocalDate importLd = s.getImportDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate expiredLd = s.getExpiredDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return new StockLotResponseDTO(
                s.getId(),
                s.getProduct().getId(),
                s.getProduct().getProductName(),
                s.getWarehouse().getId(),
                s.getQuantity(),
                importLd,
                expiredLd
        );
    }
}
