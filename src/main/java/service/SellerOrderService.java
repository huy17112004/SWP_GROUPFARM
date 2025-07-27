package service;

import dao.WholesaleOrderDAO;
import dto.OrderSellerDTO;
import dto.OrderItemSellerDTO;
import entity.WholesaleOrder;
import entity.WholesaleOrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import util.JpaUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SellerOrderService {

    public OrderSellerDTO getByOrderId(int orderId){
        EntityManager em = JpaUtil.getEntityManager();
        try {
            WholesaleOrderDAO orderDAO = new WholesaleOrderDAO(em);
            WholesaleOrder order = orderDAO.findById(orderId);
            return mapToOrderSellerDTO(order);
        } finally {
            em.close();
        }
    }

    public List<OrderSellerDTO> getAllOrdersForSeller(int sellerId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            WholesaleOrderDAO orderDao = new WholesaleOrderDAO(em);
            List<WholesaleOrder> orders = orderDao.findAllBySellerIdWithItems(sellerId);
            
            return orders.stream()
                    .map(SellerOrderService::mapToOrderSellerDTO)
                    .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    public boolean processOrderAction(int sellerId, int orderId, String action) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            WholesaleOrderDAO orderDao = new WholesaleOrderDAO(em);
            WholesaleOrder order = orderDao.findById(orderId);
            
            if (order == null) {
                return false;
            }
            
            // Kiểm tra xem order có thuộc về seller này không
            if (order.getSeller().getId() != sellerId) {
                return false;
            }
            
            // Xử lý action
            if ("ACCEPT".equals(action)) {
                // Kiểm tra xem order có status CREATED không
                if (!"CREATED".equals(order.getStatus())) {
                    return false;
                }
                order.setStatus("NEGOTIATING");
                order.setConfirmedAt(new Date());
            } else if ("REJECT".equals(action)) {
                // Kiểm tra xem order có status CREATED không
                if (!"CREATED".equals(order.getStatus())) {
                    return false;
                }
                order.setStatus("REJECTED");
            } else if ("CONFIRM".equals(action)) {
                // Kiểm tra xem order có status DEPOSIT không
                if (!"DEPOSIT".equals(order.getStatus())) {
                    return false;
                }
                order.setStatus("CONFIRMED");
                order.setConfirmedAt(new Date());
            } else {
                return false;
            }
            
            orderDao.update(order);
            tx.commit();
            return true;
            
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public static OrderSellerDTO mapToOrderSellerDTO(WholesaleOrder order) {
        // Map items
        List<OrderItemSellerDTO> items = order.getItems() != null ? order.getItems().stream()
                .map(SellerOrderService::mapToOrderItemSellerDTO)
                .collect(Collectors.toList()) : new ArrayList<>();

        // Build delivery address
        String deliveryAddress = "";
        if (order.getDeliveryAddress() != null) {
            deliveryAddress = order.getDeliveryAddress().getStreet() != null ? order.getDeliveryAddress().getStreet() : "";
            if (order.getDeliveryAddress().getWard() != null) {
                deliveryAddress += ", " + order.getDeliveryAddress().getWard().getName();
                if (order.getDeliveryAddress().getWard().getDistrict() != null) {
                    deliveryAddress += ", " + order.getDeliveryAddress().getWard().getDistrict().getName();
                    if (order.getDeliveryAddress().getWard().getDistrict().getProvince() != null) {
                        deliveryAddress += ", " + order.getDeliveryAddress().getWard().getDistrict().getProvince().getName();
                    }
                }
            }
        }

        return new OrderSellerDTO(
                order.getId(),
                "ORD-" + String.format("%06d", order.getId()), // Generate order code
                order.getCreatedAt() != null ? order.getCreatedAt() : new Date(),
                order.getStatus() != null ? order.getStatus() : "UNKNOWN",
                order.getItemsTotal()!= null ? order.getItemsTotal() : BigDecimal.ZERO,
                order.getTotalPrice() != null ? order.getTotalPrice() : BigDecimal.ZERO,
                order.getEstimatedShipFee() != null ? order.getEstimatedShipFee() : BigDecimal.ZERO,
                order.getCustomer() != null ? order.getCustomer().getUsername() : "",
                order.getCustomer() != null ? order.getCustomer().getPhone() : "",
                order.getCustomer() != null ? order.getCustomer().getEmail() : "",
                deliveryAddress,
                order.getDeliveryDate(),
                items
        );
    }

    private static OrderItemSellerDTO mapToOrderItemSellerDTO(WholesaleOrderItem item) {
        String productImage = "";
        if (item.getProduct() != null && item.getProduct().getImages() != null && !item.getProduct().getImages().isEmpty()) {
            productImage = item.getProduct().getImages().get(0).getImageUrl();
        }

        return new OrderItemSellerDTO(
                item.getId(),
                item.getProduct() != null ? item.getProduct().getProductName() : "Unknown Product",
                productImage,
                item.getPrice() != null ? item.getPrice() : BigDecimal.ZERO,
                item.getQuantity(),
                item.getSubTotal() != null ? item.getSubTotal() : BigDecimal.ZERO
        );
    }
} 