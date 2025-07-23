package dao;

import dto.TopProductDTO;
import entity.WholesaleOrder;
import jakarta.persistence.EntityManager;
import dto.ShippingOrderDTO;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import entity.Product;

public class StatsDAO  extends GenericDAO{

    public StatsDAO(EntityManager em){
        super(WholesaleOrder.class, em);
    }

    public int getWholesaleOrdersToday(){
        // Tạo ngày hôm nay (00:00:00)
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfDay = cal.getTime();
        
        // Tạo ngày mai (00:00:00)
        cal.add(Calendar.DATE, 1);
        Date startOfNextDay = cal.getTime();
        
        String jpql = "SELECT COUNT(o.id) FROM WholesaleOrder o WHERE o.createdAt >= :startDate AND o.createdAt < :endDate";
        Long count = em.createQuery(jpql, Long.class)
                      .setParameter("startDate", startOfDay)
                      .setParameter("endDate", startOfNextDay)
                      .getSingleResult();
        return count != null ? count.intValue() : 0;
    }


    public List<ShippingOrderDTO> getAllPendingOrShippedOrders() {
        String jpql = """
        SELECT new dto.ShippingOrderDTO(
            o.id,
            p.productName,
            o.status,
            o.totalPrice
        )
        FROM WholesaleOrder o
        JOIN o.items i
        JOIN i.product p
        WHERE o.status IN ('SHIPPED', 'PENDING')
    """;

        return em.createQuery(jpql, ShippingOrderDTO.class).getResultList();
    }


    //Tính doanh thu hôm nay (chỉ đơn hàng hoàn thành)

    public BigDecimal getTodayRevenue() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);    
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfDay = cal.getTime();

        cal.add(Calendar.DATE, 1);
        Date startOfNextDay = cal.getTime();

        String jpql = "SELECT COALESCE(SUM(o.totalPrice), 0) FROM WholesaleOrder o " +
                "WHERE o.createdAt >= :startDate AND o.createdAt < :endDate " +
                "AND o.status = 'SHIPPED'";

        BigDecimal result = em.createQuery(jpql, BigDecimal.class)
                .setParameter("startDate", startOfDay)
                .setParameter("endDate", startOfNextDay)
                .getSingleResult();

        return result != null ? result : BigDecimal.ZERO;
    }


    //Tính doanh thu tuần này (từ thứ 2 đến chủ nhật)

    public BigDecimal getWeekRevenue() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfWeek = cal.getTime();

        cal.add(Calendar.WEEK_OF_YEAR, 1);
        Date startOfNextWeek = cal.getTime();

        String jpql = "SELECT COALESCE(SUM(o.totalPrice), 0) FROM WholesaleOrder o " +
                "WHERE o.createdAt >= :startDate AND o.createdAt < :endDate " +
                "AND o.status = 'SHIPPED'";

        BigDecimal result = em.createQuery(jpql, BigDecimal.class)
                .setParameter("startDate", startOfWeek)
                .setParameter("endDate", startOfNextWeek)
                .getSingleResult();

        return result != null ? result : BigDecimal.ZERO;
    }
    // Tính doanh thu tháng này

    public BigDecimal getMonthRevenue() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfMonth = cal.getTime();

        cal.add(Calendar.MONTH, 1);
        Date startOfNextMonth = cal.getTime();

        String jpql = "SELECT COALESCE(SUM(o.totalPrice), 0) FROM WholesaleOrder o " +
                "WHERE o.createdAt >= :startDate AND o.createdAt < :endDate " +
                "AND o.status = 'SHIPPED'";

        BigDecimal result = em.createQuery(jpql, BigDecimal.class)
                .setParameter("startDate", startOfMonth)
                .setParameter("endDate", startOfNextMonth)
                .getSingleResult();

        return result != null ? result : BigDecimal.ZERO;
    }



    //Tính doanh thu năm này
    public BigDecimal getYearRevenue() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfYear = cal.getTime();

        cal.add(Calendar.YEAR, 1);
        Date startOfNextYear = cal.getTime();

        String jpql = "SELECT COALESCE(SUM(o.totalPrice), 0) FROM WholesaleOrder o " +
                "WHERE o.createdAt >= :startDate AND o.createdAt < :endDate " +
                "AND o.status = 'SHIPPED'";

        BigDecimal result = em.createQuery(jpql, BigDecimal.class)
                .setParameter("startDate", startOfYear)
                .setParameter("endDate", startOfNextYear)
                .getSingleResult();

        return result != null ? result : BigDecimal.ZERO;
    }



    /**
     * Lấy top sản phẩm bán chạy
     * @param limit Số lượng sản phẩm muốn lấy (top N)
     * @param startDate Ngày bắt đầu
     * @param endDate Ngày kết thúc
     * @param sortBy Sắp xếp theo 'quantity' hoặc 'revenue'
     * @return Danh sách top sản phẩm
     */
    public List<TopProductDTO> getTopProducts(int limit, Date startDate, Date endDate, String sortBy) {
        String orderBy = "totalQuantity DESC";
        if ("revenue".equalsIgnoreCase(sortBy)) {
            orderBy = "totalRevenue DESC";
        }
        String jpql = "SELECT p.id, p.productName, p.retailPrice, " +
                "SUM(oi.quantity) as totalQuantity, " +
                "SUM(oi.subTotal) as totalRevenue " +
                "FROM WholesaleOrderItem oi " +
                "JOIN oi.product p " +
                "JOIN oi.order o " +
                "WHERE o.createdAt >= :startDate " +
                "AND o.createdAt < :endDate " +
                "AND o.status = 'SHIPPED' " +
                "GROUP BY p.id, p.productName, p.retailPrice " +
                "ORDER BY " + orderBy;

        List<Object[]> results = em.createQuery(jpql, Object[].class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setMaxResults(limit)
                .getResultList();

        List<TopProductDTO> topProducts = new ArrayList<>();
        int rank = 1;
        for (Object[] row : results) {
            Integer productId = (Integer) row[0];
            String productName = (String) row[1];
            BigDecimal price = (row[2] instanceof Integer) ? new BigDecimal((Integer) row[2]) : (BigDecimal) row[2];
            Long totalQuantity = (Long) row[3];
            BigDecimal totalRevenue = (BigDecimal) row[4];

            // Lấy imageUrl bằng code Java
            String imageUrl = null;
            Product product = em.find(Product.class, productId);
            if (product != null && product.getImages() != null && !product.getImages().isEmpty()) {
                imageUrl = product.getImages().get(0).getImageUrl();
            }

            TopProductDTO dto = new TopProductDTO(
                    productId,
                    productName,
                    totalQuantity != null ? totalQuantity.intValue() : 0,
                    totalRevenue != null ? totalRevenue : BigDecimal.ZERO,
                    imageUrl,
                    rank++,
                    price
            );
            topProducts.add(dto);
        }
        return topProducts;
    }

    /**
     * Lấy top sản phẩm bán chạy hôm nay
     * @param sortBy Sắp xếp theo 'quantity' hoặc 'revenue'
     */
    public List<TopProductDTO> getTopProductsToday(int limit, String sortBy) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfDay = cal.getTime();
        cal.add(Calendar.DATE, 1);
        Date endOfDay = cal.getTime();
        return getTopProducts(limit, startOfDay, endOfDay, sortBy);
    }

    /**
     * Lấy top sản phẩm bán chạy tuần này
     * @param sortBy Sắp xếp theo 'quantity' hoặc 'revenue'
     */
    public List<TopProductDTO> getTopProductsThisWeek(int limit, String sortBy) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfWeek = cal.getTime();
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        Date endOfWeek = cal.getTime();
        return getTopProducts(limit, startOfWeek, endOfWeek, sortBy);
    }

    /**
     * Lấy top sản phẩm bán chạy tháng này
     * @param sortBy Sắp xếp theo 'quantity' hoặc 'revenue'
     */
    public List<TopProductDTO> getTopProductsThisMonth(int limit, String sortBy) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfMonth = cal.getTime();
        cal.add(Calendar.MONTH, 1);
        Date endOfMonth = cal.getTime();
        return getTopProducts(limit, startOfMonth, endOfMonth, sortBy);
    }

    public int getTotalSoldQuantity() {
        String jpql = "SELECT COALESCE(SUM(oi.quantity), 0) FROM WholesaleOrderItem oi " +
                "JOIN oi.order o " +
                "WHERE o.status = 'SHIPPED'";
        Long totalSold = em.createQuery(jpql, Long.class).getSingleResult();
        return totalSold != null ? totalSold.intValue() : 0;
    }

}



