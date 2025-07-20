package dao;

import dto.DealRequestDTO;
//import dto.DealRequestFilterDTO;
import dto.OrderItemDTO;
import entity.DealRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DealRequestDAO extends GenericDAO<DealRequest> {
    public DealRequestDAO(EntityManager entityManager) {
        super(DealRequest.class, entityManager);
    }

    public DealRequest findLastByOrderItemId(int orderItemId) {
        List<DealRequest> results = em.createQuery(
                        "SELECT d FROM DealRequest d " +
                                "WHERE d.orderItem.id = :itemId " +
                                "ORDER BY d.requestedAt DESC",
                        DealRequest.class
                ).setParameter("itemId", orderItemId)
                .setMaxResults(1)
                .getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    // 1. Check tồn tại deal với status cho trước
    public boolean existsByOrderItemIdAndStatus(Integer orderItemId, String status) {
        Long count = em.createQuery(
                        "SELECT COUNT(d) FROM DealRequest d " +
                                "WHERE d.orderItem.id = :itemId AND d.status = :status",
                        Long.class)
                .setParameter("itemId", orderItemId)
                .setParameter("status", status)
                .getSingleResult();
        return count > 0;
    }

    // 2. Lấy mức giá đề nghị cao nhất đã bị REJECTED
    public BigDecimal findMaxRejectedPriceByOrderItemId(Integer orderItemId) {
        return em.createQuery(
                        "SELECT MAX(d.proposedPrice) FROM DealRequest d " +
                                "WHERE d.orderItem.id = :itemId AND d.status = 'REJECTED'",
                        BigDecimal.class)
                .setParameter("itemId", orderItemId)
                .getSingleResult();
    }
    public DealRequestDTO mapToDTO(DealRequest dr) {
        DealRequestDTO drDTO = new DealRequestDTO();
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(dr.getOrderItem().getId());
        orderItemDTO.setQuantity(dr.getOrderItem().getQuantity());
        orderItemDTO.setUnitPrice(dr.getOrderItem().getPrice());
        orderItemDTO.setOrderId(dr.getOrderItem().getOrder().getId());
        orderItemDTO.setProductId(dr.getOrderItem().getProduct().getId());
        orderItemDTO.setProductName(dr.getOrderItem().getProduct().getProductName());

        drDTO.setId(dr.getId());
        drDTO.setOrderItem(orderItemDTO);
        drDTO.setProposedPrice(dr.getProposedPrice());
        drDTO.setStatus(dr.getStatus());
        drDTO.setRequestedAt(dr.getRequestedAt());
        drDTO.setMessage(dr.getMessage());
        drDTO.setCustomerName(dr.getOrderItem().getOrder().getCustomer().getCompanyName());
        drDTO.setTotalOriginalPrice(dr.getOrderItem().getSubTotal());
        drDTO.setTotalProposedPrice(dr.getProposedPrice().multiply(new BigDecimal(orderItemDTO.getQuantity())));
        drDTO.setDiscountAmount(drDTO.getTotalOriginalPrice().subtract(drDTO.getTotalProposedPrice()));
        drDTO.setDiscountRate(drDTO.getDiscountAmount().divide(drDTO.getTotalOriginalPrice(), 2, BigDecimal.ROUND_HALF_UP));
        return drDTO;
    }

}
