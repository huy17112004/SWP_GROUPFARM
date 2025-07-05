package service;

import dao.DealRequestDAO;
import dao.WholesaleOrderItemDAO;
import dto.*;
import entity.DealRequest;
import entity.WholesaleOrderItem;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DealRequestService {

    /**
     * Tạo mới một DealRequest cho orderItem đã cho.
     */
    public DealRequestDTO createDealRequest(DealRequestCreateDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();


        try {
            em.getTransaction().begin();
            DealRequestDAO dealRequestDAO = new DealRequestDAO(em);
            WholesaleOrderItemDAO orderItemDAO = new WholesaleOrderItemDAO(em);

            // 1. Entity tồn tại?
            WholesaleOrderItem item = orderItemDAO.findById(dto.getOrderItemId());
            if (item == null) {
                throw new IllegalArgumentException("Đơn hàng không tồn tại");
            }

            // 1. Đã từng APPROVED?
            if (dealRequestDAO.existsByOrderItemIdAndStatus(item.getId(), "APPROVED")) {
                throw new IllegalStateException("Đơn hàng đã được deal thành công! Không thể tạo deal mới!");
            }

            // 2. Đang PENDING?
            if (dealRequestDAO.existsByOrderItemIdAndStatus(item.getId(), "PENDING")) {
                throw new IllegalStateException("Đơn hàng đang chờ deal! Không thể tạo deal mới!");
            }

            // 3. Giá phải lớn hơn mức REJECTED cao nhất
            BigDecimal maxRejected = dealRequestDAO.findMaxRejectedPriceByOrderItemId(item.getId());
            if (maxRejected != null
                    && dto.getProposedPrice().compareTo(maxRejected) <= 0) {
                throw new IllegalStateException(
                        "Giá đề nghị phải cao hơn mức " + maxRejected.stripTrailingZeros().toPlainString() + "VND đã bị từ chối trước đó.");
            }

            // Khởi tạo DealRequest
            DealRequest dr = new DealRequest();
            dr.setOrderItem(item);
            dr.setProposedPrice(dto.getProposedPrice());
            dr.setStatus("PENDING");
            dr.setMessage(dto.getMessage());
            dr.setRequestedAt(LocalDateTime.now());

            dealRequestDAO.save(dr);
            em.getTransaction().commit();
            return dealRequestDAO.mapToDTO(dr);
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
    public List<DealRequestDTO> listDealRequests(DealRequestFilterDTO filter) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            DealRequestDAO dealDao = new DealRequestDAO(em);
            return dealDao.findByFilter(filter);
        } finally {
            em.close();
        }
    }

    public DealRequestDTO rejectDeal(DealConfirmDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            DealRequestDAO dealDao = new DealRequestDAO(em);

            // 1. Tìm DealRequest theo dealId
            DealRequest dr = dealDao.findById(dto.getDealId());
            if (dr == null) {
                throw new IllegalArgumentException("DealRequest không tồn tại: id=" + dto.getDealId());
            }

            // 2. Chỉ cho reject nếu đang ở trạng thái PENDING
            if (!"PENDING".equals(dr.getStatus())) {
                throw new IllegalStateException("Chỉ những request đang PENDING mới có thể reject.");
            }

            // 3. Cập nhật trạng thái, message và respondedAt
            dr.setStatus("REJECTED");
            dr.setMessage(dto.getMessage());
            dr.setRespondedAt(LocalDateTime.now());

            em.getTransaction().commit();

            // 5. Trả về DTO đã cập nhật
            return dealDao.mapToDTO(dr);

        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public DealRequestDTO approveDeal(DealConfirmDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            DealRequestDAO dealDao = new DealRequestDAO(em);

            // 1. Tìm entity
            DealRequest dr = dealDao.findById(dto.getDealId());
            if (dr == null) {
                throw new IllegalArgumentException("DealRequest không tồn tại: id=" + dto.getDealId());
            }

            // 2. Chỉ approve khi đang PENDING
            if (!"PENDING".equals(dr.getStatus())) {
                throw new IllegalStateException("Chỉ những request ở trạng thái PENDING mới có thể APPROVE.");
            }

            // 3. Cập nhật trạng thái, message, respondedAt
            dr.setStatus("APPROVED");
            dr.setMessage(dto.getMessage());
            dr.setRespondedAt(LocalDateTime.now());
            dr.getOrderItem().setPrice(dr.getProposedPrice());
            dr.getOrderItem().setSubTotal(dr.getProposedPrice().multiply(BigDecimal.valueOf(dr.getOrderItem().getQuantity())));

            em.getTransaction().commit();

            // 5. Trả về DTO
            return dealDao.mapToDTO(dr);
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public List<DealRequestCustomerDTO> listDealsForOrder(int orderId, int customerId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            DealRequestDAO dao = new DealRequestDAO(em);
            List<DealRequest> list = dao.findByOrderAndCustomer(orderId, customerId);
            return list.stream()
                    .map(d -> new DealRequestCustomerDTO(
                            d.getId(),
                            d.getOrderItem().getId(),
                            d.getOrderItem().getProduct().getProductName(),
                            d.getOrderItem().getQuantity(),
                            d.getOrderItem().getPrice(),
                            d.getProposedPrice(),
                            d.getStatus(),
                            d.getRequestedAt(),
                            d.getRespondedAt(),
                            d.getMessage()
                    ))
                    .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }


}
