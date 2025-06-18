package service;

import dao.WarehouseDAO;
import entity.Address;
import entity.Warehouse;
import jakarta.persistence.EntityManager;

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


}
