package service;

import dao.StockLotDAO;
import dao.StockTransferDAO;
import dao.WholesaleOrderDAO;
import dto.StockTransferResponseDTO;
import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import util.JpaUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StockTransferService {
    public List<StockTransferResponseDTO> startStockTransferForOrder(int orderId) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            WholesaleOrderDAO wholesaleOrderDAO = new WholesaleOrderDAO(em);
            StockTransferDAO stockTransferDAO = new StockTransferDAO(em);

            WholesaleOrder order = wholesaleOrderDAO.findById(orderId);
            order.setStatus("CONSOLIDATING");
            List<StockTransfer> stockTransfers = new ArrayList<>();
            order.getItems().forEach(item -> {
                item.getOrderItemAllocations().forEach(orderItemAllocation -> {
                    StockLot stockLot = orderItemAllocation.getStockLot();
                    Warehouse lotWarehouse = stockLot.getWarehouse();
                    Warehouse sourceWarehouse = order.getSourceWarehouse();
                    if (lotWarehouse.getId() != sourceWarehouse.getId()) {
                        // Tạo StockTransferItem
                        StockTransferItem stockTransferItem = new StockTransferItem();
                        stockTransferItem.setStockLot(stockLot);
                        stockTransferItem.setQuantity(orderItemAllocation.getQuantity());
                        stockTransferItem.setOrderItemAllocation(orderItemAllocation);

                        // tìm stockTransfer mà có điểm suất hàng giống với stocklot
                        StockTransfer stockTransfer = stockTransfers.stream()
                                .filter(st -> st.getSourceWarehouse().getId() == stockLot.getWarehouse().getId())
                                .findFirst()
                                .orElse(null);

                        // không có thì tạo stockTransfer mới
                        if (stockTransfer == null) {
                            stockTransfer = new StockTransfer();
                            stockTransfer.setOrder(order);
                            stockTransfer.setSourceWarehouse(lotWarehouse);
                            stockTransfer.setDestinationWarehouse(sourceWarehouse); // giả sử chuyển về nguồn
                            stockTransfer.setStatus("PENDING");
                            stockTransfer.setRequestedAt(LocalDateTime.now());
                            stockTransfer.setItems(new ArrayList<>());
                            stockTransfers.add(stockTransfer);
                        }
                        stockTransferItem.setStockTransfer(stockTransfer);
                        orderItemAllocation.setStockTransferItem(stockTransferItem);
                        stockTransfer.getItems().add(stockTransferItem);
                    }
                });
            });

            for (StockTransfer st : stockTransfers) {
                stockTransferDAO.save(st);
            }
            tx.commit();
            return stockTransfers.stream().map(StockTransferResponseDTO::new).collect(Collectors.toList());
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }

    }

    public StockTransferResponseDTO exportStockTransfer(int stockTransferId) {
        EntityManager em = JpaUtil.getEntityManager();
        StockTransferDAO stockTransferDAO = new StockTransferDAO(em);
        StockTransfer stockTransfer = stockTransferDAO.findById(stockTransferId);
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            stockTransfer.setStatus("EXPORTED");
            stockTransfer.getItems()
                    .forEach(item -> {
                        OrderItemAllocation orderItemAllocation = item.getOrderItemAllocation();
                        orderItemAllocation.getStockLot().setQuantity(orderItemAllocation.getStockLot().getQuantity() - orderItemAllocation.getQuantity());
                        item.getOrderItemAllocation().setStockLot(null);
                    });
            stockTransferDAO.save(stockTransfer);
            tx.commit();
            return new StockTransferResponseDTO(stockTransfer);
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public StockTransferResponseDTO completeStockTransfer(int stockTransferId) {
        EntityManager em = JpaUtil.getEntityManager();
        StockTransferDAO stockTransferDAO = new StockTransferDAO(em);
        StockLotDAO stockLotDAO = new StockLotDAO(em);
        StockTransfer stockTransfer = stockTransferDAO.findById(stockTransferId);
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            stockTransfer.setStatus("COMPLETED");
            stockTransfer.getItems()
                    .forEach(item -> {
                        StockLot stockLot = new StockLot();
                        stockLot.setProduct(item.getStockLot().getProduct());
                        stockLot.setQuantity(item.getQuantity());
                        stockLot.setWarehouse(stockTransfer.getDestinationWarehouse());
                        stockLot.setImportDate(item.getStockLot().getImportDate());
                        stockLot.setExpiredDate(item.getStockLot().getExpiredDate());
                        stockLotDAO.save(stockLot);
                        item.setStockLot(stockLot);
                        item.getOrderItemAllocation().setStockLot(stockLot);
                    });
            stockTransferDAO.save(stockTransfer);
            tx.commit();
            return new StockTransferResponseDTO(stockTransfer);
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
