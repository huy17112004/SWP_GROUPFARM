package dao;

import dto.DealRequestDTO;
import dto.DealRequestFilterDTO;
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

    public List<DealRequest> findByOrderItemId(int orderItemId) {
        return em.createQuery(
                        "SELECT d FROM DealRequest d WHERE d.orderItem.id = :itemId", DealRequest.class)
                .setParameter("itemId", orderItemId)
                .getResultList();
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

    public List<DealRequestDTO> findByFilter(DealRequestFilterDTO f) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT d FROM DealRequest d ")
                .append(" JOIN d.orderItem oi")
                .append(" JOIN oi.product p")
                .append(" JOIN oi.order o")
                .append(" JOIN o.customer c")
                .append(" WHERE 1=1");

        Map<String,Object> params = new HashMap<>();

        // 1. status IN (…)
        if (f.getStatuses() != null && !f.getStatuses().isEmpty()) {
            jpql.append(" AND d.status IN :statuses");
            params.put("statuses", f.getStatuses());
        }

        // 2. customerName LIKE …
        if (f.getCustomerName() != null && !f.getCustomerName().isBlank()) {
            jpql.append(" AND LOWER(c.companyName) LIKE :custName");
            params.put("custName", "%" + f.getCustomerName().toLowerCase() + "%");
        }

        // 3. productId, productName
        if (f.getProductId() != null) {
            jpql.append(" AND p.id = :prodId");
            params.put("prodId", f.getProductId());
        }
        if (f.getProductName() != null && !f.getProductName().isBlank()) {
            jpql.append(" AND LOWER(p.productName) LIKE :prodName");
            params.put("prodName", "%" + f.getProductName().toLowerCase() + "%");
        }

        // 4. totalOriginalPrice = oi.subTotal
        if (f.getMinTotalOriginalPrice() != null) {
            jpql.append(" AND oi.subTotal >= :minOrig");
            params.put("minOrig", f.getMinTotalOriginalPrice());
        }
        if (f.getMaxTotalOriginalPrice() != null) {
            jpql.append(" AND oi.subTotal <= :maxOrig");
            params.put("maxOrig", f.getMaxTotalOriginalPrice());
        }

        // 5. totalProposedPrice = d.proposedPrice * oi.quantity
        if (f.getMinTotalProposedPrice() != null) {
            jpql.append(" AND (d.proposedPrice * oi.quantity) >= :minProp");
            params.put("minProp", f.getMinTotalProposedPrice());
        }
        if (f.getMaxTotalProposedPrice() != null) {
            jpql.append(" AND (d.proposedPrice * oi.quantity) <= :maxProp");
            params.put("maxProp", f.getMaxTotalProposedPrice());
        }

        // 6. discountRate = (oi.subTotal - d.proposedPrice * oi.quantity) / oi.subTotal
        if (f.getMinDiscountRate() != null) {
            jpql.append(" AND ((oi.subTotal - (d.proposedPrice * oi.quantity)) / oi.subTotal) >= :minRate");
            params.put("minRate", f.getMinDiscountRate());
        }
        if (f.getMaxDiscountRate() != null) {
            jpql.append(" AND ((oi.subTotal - (d.proposedPrice * oi.quantity)) / oi.subTotal) <= :maxRate");
            params.put("maxRate", f.getMaxDiscountRate());
        }

        // 7. quantity
        if (f.getMinQuantity() != null) {
            jpql.append(" AND oi.quantity >= :minQty");
            params.put("minQty", f.getMinQuantity());
        }
        if (f.getMaxQuantity() != null) {
            jpql.append(" AND oi.quantity <= :maxQty");
            params.put("maxQty", f.getMaxQuantity());
        }

        // 9. Sort
        if (f.getSortField() != null) {
            String dir = f.isSortAsc() ? "ASC" : "DESC";
            String sortClause = null;
            switch (f.getSortField()) {
                case "requestedAt":
                    sortClause = "d.requestedAt";
                    break;
                case "totalOriginalPrice":
                    // totalOriginalPrice = oi.subTotal
                    sortClause = "oi.subTotal";
                    break;
                case "totalProposedPrice":
                    // totalProposedPrice = d.proposedPrice * oi.quantity
                    sortClause = "(d.proposedPrice * oi.quantity)";
                    break;
                case "discountRate":
                    // discountRate = (oi.subTotal - (d.proposedPrice * oi.quantity)) / oi.subTotal
                    sortClause = "((oi.subTotal - (d.proposedPrice * oi.quantity)) / oi.subTotal)";
                    break;
                default:
                    // không có sort
            }
            if (sortClause != null) {
                jpql.append(" ORDER BY ").append(sortClause).append(" ").append(dir);
            }
        }

        TypedQuery<DealRequest> q = em.createQuery(jpql.toString(), DealRequest.class);
        params.forEach(q::setParameter);
        List<DealRequest> entities = q.getResultList();
        // map sang DTO
        List<DealRequestDTO> dtos = new ArrayList<>();
        for (DealRequest d : entities) {
            dtos.add(mapToDTO(d));
        }
        return dtos;
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

    public List<DealRequest> findByOrderAndCustomer(int orderId, int customerId) {
        TypedQuery<DealRequest> q = em.createQuery(
                "SELECT d FROM DealRequest d " +
                        " JOIN FETCH d.orderItem oi " +
                        " JOIN FETCH oi.product p " +
                        " JOIN FETCH oi.order o " +
                        " WHERE o.id = :orderId " +
                        "   AND o.customer.id = :custId",
                DealRequest.class
        );
        q.setParameter("orderId", orderId);
        q.setParameter("custId", customerId);
        return q.getResultList();
    }

}
