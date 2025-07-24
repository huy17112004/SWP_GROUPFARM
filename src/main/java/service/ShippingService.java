package service;

import dao.*;
import dto.ShippingStatsDTO;
import dto.StockTransferResponseDTO;
import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import util.JpaUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

public class ShippingService {

    public double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Bán kính Trái Đất tính bằng km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // khoảng cách tính bằng km
    }

    public double estimateDeliveryTime(double distanceKm, double averageSpeedKmph) {
        return distanceKm / averageSpeedKmph; // thời gian tính bằng giờ
    }

    public double calculateShippingFee(double distanceKm, double weightKg, double unitPricePerKgPerKm) {
        return distanceKm * weightKg * unitPricePerKgPerKm;
    }

    public BigDecimal calculateShippingFee(double distanceKm, List<Cart> carts, EntityManager em) {
        ShippingRequirementDAO shippingRequirementDAO = new ShippingRequirementDAO(em);

        BigDecimal distance = BigDecimal.valueOf(distanceKm);

        return carts.stream()
                .map(cart -> {
                    BigDecimal rate = shippingRequirementDAO.findByProductId(cart.getProduct().getId()).getRatePerKmPerKg();
                    BigDecimal quantity = BigDecimal.valueOf(cart.getQuantity());
                    return distance.multiply(quantity).multiply(rate);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Warehouse findNearestWarehouseOnTime(
            List<Warehouse> warehouses,
            double customerLat,
            double customerLon,
            LocalDateTime deliveryDate,
            double avgSpeedKmph
    ) {
        LocalDateTime now = LocalDateTime.now();

        int gatheringHours = 6;

        return warehouses.stream()
                .filter(wh -> {
                    // chỉ lấy kho có tọa độ đầy đủ
                    Address a = wh.getAddress();
                    return a != null && a.getLatitude() != null && a.getLongitude() != null;
                })
                .filter(wh -> {
                    // tính khoảng cách
                    Address a = wh.getAddress();
                    double dist = haversine(customerLat, customerLon, a.getLatitude(), a.getLongitude());

                    // tính thời gian cần thiết (giờ)
                    double travelHours = dist / avgSpeedKmph;

                    // tính thời điểm khởi hành sau khi đã gom kho xong
                    LocalDateTime departure = now.plusHours(gatheringHours);
                    // tính thời điểm đến
                    LocalDateTime arrival = departure.plusMinutes((long)(travelHours * 60));
                    // giữ lại nếu kịp trước hoặc đúng hạn
                    return !arrival.isAfter(deliveryDate);
                })
                // chọn kho có khoảng cách nhỏ nhất
                .min(Comparator.comparingDouble(wh -> {
                    Address a = wh.getAddress();
                    return haversine(customerLat, customerLon, a.getLatitude(), a.getLongitude());
                }))
                .orElse(null);
    }

    public BigDecimal calculateEstimateShippingFee(int accountId, int addressId, LocalDateTime deliveryDate, double avgSpeedKmph) {
        EntityManager em = JpaUtil.getEntityManager();
        WarehouseDAO warehouseDAO = new WarehouseDAO(em);
        AddressDAO addressDAO = new AddressDAO(em);
        CartDAO cartDAO = new CartDAO(em);
        List<Cart> carts = cartDAO.findAllByCustomerId(accountId);
        Address deliveryAddress = addressDAO.findById(addressId);
        float latitude =  deliveryAddress.getLatitude();
        float longitude = deliveryAddress.getLongitude();
        List<Warehouse> warehouses = warehouseDAO.findAll();
        Warehouse destWarehouse = findNearestWarehouseOnTime(
                warehouses, latitude, longitude, deliveryDate, avgSpeedKmph
        );

        double distanceKm = haversine(
                destWarehouse.getAddress().getLatitude(),
                destWarehouse.getAddress().getLongitude(),
                latitude, longitude);

        return calculateShippingFee(distanceKm, carts, em);
    }


    public boolean exportOrderToShipper(int orderId, int shipperId) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        WholesaleOrderDAO wholesaleOrderDAO = new WholesaleOrderDAO(em);
        ShipperDAO shipperDAO = new ShipperDAO(em);
        ShippingLogDAO shippingLogDAO = new ShippingLogDAO(em);
        Shipper shipper = shipperDAO.findById(shipperId);
        WholesaleOrder order = wholesaleOrderDAO.findById(orderId);
        try {
            tx.begin();
            order.setStatus("SHIPPING");
            order.getItems()
                    .forEach(item -> {
                        item.getOrderItemAllocations().forEach(orderItemAllocation -> {
                            orderItemAllocation.getStockLot().setQuantity(orderItemAllocation.getStockLot().getQuantity() - orderItemAllocation.getQuantity());
                            orderItemAllocation.setStockLot(null);
                        });
                    });
            ShippingLog shippingLog = new ShippingLog();
            shippingLog.setShipper(shipper);
            shippingLog.setOrder(order);
            shippingLogDAO.save(shippingLog);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }




}
