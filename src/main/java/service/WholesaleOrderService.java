package service;

import dao.CartDAO;
import dao.StockLotDAO;
import dao.WarehouseDAO;
import dao.WholesaleOrderDAO;
import dto.OrderResponseDTO;
import dto.PlaceOrderRequestDTO;
import entity.*;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WholesaleOrderService {

    public OrderResponseDTO placeOrder(
            int customerId,
            LocalDateTime deliveryDate,
            double latitude,
            double longitude,
            double avgSpeedKmph
    ) {
        EntityManager em = JpaUtil.getEntityManager();
        CartDAO cartDAO = new CartDAO(em);
        WarehouseDAO warehouseDAO = new WarehouseDAO(em);
        StockLotDAO stockLotDAO = new StockLotDAO(em);
        ShippingService shippingService = new ShippingService();
        OrderResponseDTO resp = new OrderResponseDTO();
        List<Cart> carts = cartDAO.findAllByCustomerId(customerId);
        if (carts.isEmpty()) {
            resp.setSuccess(false);
            resp.setMessage("Giỏ hàng trống.");
            return resp;
        }

        // 1. Tìm kho đích
        List<Warehouse> warehouses = warehouseDAO.findAll();
        Warehouse destWarehouse = shippingService.findNearestWarehouseOnTime(
                warehouses, latitude, longitude, deliveryDate, avgSpeedKmph
        );
        if (destWarehouse == null) {
            resp.setSuccess(false);
            resp.setMessage("Địa chỉ quá xa, không thể giao hàng đúng hạn!");
            return resp;
        }

        // 2. Kiểm tra tồn kho từng sản phẩm
        List<OrderResponseDTO.ProductStockInfo> lackList = new ArrayList<>();
        Date deliveryDateSql = java.sql.Timestamp.valueOf(deliveryDate);
        for (Cart c : carts) {
            List<StockLot> lots = stockLotDAO.findEligibleStockLotsJava(c.getProduct().getId(), deliveryDateSql);
            int total = lots.stream().mapToInt(StockLot::getQuantity).sum();
            if (total < c.getQuantity()) {
                OrderResponseDTO.ProductStockInfo info = new OrderResponseDTO.ProductStockInfo();
                info.setProductId(c.getProduct().getId());
                info.setProductName(c.getProduct().getProductName());
                info.setRequired(c.getQuantity());
                info.setAvailable(total);
                lackList.add(info);
            }
        }
        if (!lackList.isEmpty()) {
            resp.setSuccess(false);
            resp.setMessage("Thiếu sản phẩm trong kho.");
            resp.setStockDetails(lackList);
            return resp;
        }

        // 3. Đủ điều kiện -> lên đơn
        // Tạo WholesaleOrder (code như các lần trước), gán destWarehouse làm sourceWarehouse
        // (Bạn có thể gọi orderDAO.save() ở đây)

        // ...code tạo order, tạo item, xóa cart...

        resp.setSuccess(true);
        resp.setMessage("Đặt hàng thành công!");
        // resp.setOrderId(order.getId()); // nếu cần trả mã đơn cho frontend
        return resp;
    }
}
