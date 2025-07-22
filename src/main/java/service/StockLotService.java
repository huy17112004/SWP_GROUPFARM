package service;

import dao.StockLotDAO;
import dao.WholesaleOrderDAO;
import dto.InventoryResponseDTO;
import dto.StockLotAllocationDTO;
import dto.StockLotRequestDTO;
import dto.StockLotResponseDTO;
import entity.OrderItemAllocation;
import entity.Product;
import entity.StockLot;
import entity.WholesaleOrder;
import util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class StockLotService {

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


    public StockLotResponseDTO getStockLotById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StockLot s = new StockLotDAO(em).findById(id);
            return (s != null) ? toDTO(s) : null;
        } finally {
            em.close();
        }
    }


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
            s.setImportDate(req.getImportDate());
            s.setExpiredDate(req.getExpiredDate());

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

            s.setImportDate(req.getImportDate());
            s.setExpiredDate(req.getExpiredDate());

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

    private StockLotResponseDTO toDTO(StockLot s) {
        return new StockLotResponseDTO(
                s.getId(),
                s.getProduct().getId(),
                s.getProduct().getProductName(),
                s.getWarehouse().getId(),
                s.getQuantity(),
                s.getImportDate(),
                s.getExpiredDate()
        );
    }

    public List<StockLotAllocationDTO> getAllocationByOrderId(int orderId) {
        EntityManager em = JpaUtil.getEntityManager();
        WholesaleOrderDAO wholesaleOrderDAO = new WholesaleOrderDAO(em);
        WholesaleOrder wholesaleOrder = wholesaleOrderDAO.findById(orderId);
        StockLotDAO stockLotDAO = new StockLotDAO(em);
        List<StockLotAllocationDTO> dtos = new ArrayList<>();
        wholesaleOrder.getItems().stream().forEach(item -> {
            List<StockLot> stockLots = stockLotDAO.findEligibleStockLotsJava(item.getProduct().getId(), java.sql.Timestamp.valueOf(wholesaleOrder.getDeliveryDate()));
            StockLotAllocationDTO stockLotAllocationDTO = new StockLotAllocationDTO(item);
            for (StockLot stockLot : stockLots) {
                int remainingQuantity = stockLot.getQuantity() - stockLot.getOrderItemAllocations().stream().mapToInt(OrderItemAllocation::getQuantity).sum();
                Optional<OrderItemAllocation> optionalAllocation = stockLot.getOrderItemAllocations().stream()
                        .filter(ia -> ia.getOrderItem().getId() == item.getId())
                        .findFirst();

                if (optionalAllocation.isPresent()) {
                    OrderItemAllocation takenAllocation = optionalAllocation.get();
                    stockLotAllocationDTO.getAllocationList().add(new StockLotAllocationDTO.AllocationInformation(
                            stockLot.getId(),
                            takenAllocation.getQuantity(),
                            remainingQuantity + takenAllocation.getQuantity(),
                            stockLot.getExpiredDate(),
                            stockLot.getWarehouse().getWarehouseName())
                    );
                } else {
                    if (remainingQuantity != 0) {
                        stockLotAllocationDTO.getAllocationList().add(new StockLotAllocationDTO.AllocationInformation(
                                stockLot.getId(),
                                0,
                                remainingQuantity,
                                stockLot.getExpiredDate(),
                                stockLot.getWarehouse().getWarehouseName())
                        );
                    }
                }

            }
            dtos.add(stockLotAllocationDTO);
        });
        em.close();
        return dtos;
    }
}
