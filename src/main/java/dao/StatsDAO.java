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

    public List<Object[]> getWeeklyRevenueByDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfWeek = cal.getTime();

        cal.add(Calendar.WEEK_OF_YEAR, 1);
        Date startOfNextWeek = cal.getTime();

        String sql = """
        SELECT FORMAT(createdAt, 'yyyy-MM-dd') AS day,
               SUM(totalPrice) AS revenue
        FROM WholesaleOrder
        WHERE createdAt >= ? AND createdAt < ? AND status = 'SHIPPED'
        GROUP BY FORMAT(createdAt, 'yyyy-MM-dd')
        ORDER BY day
    """;

        return em.createNativeQuery(sql)
                .setParameter(1, startOfWeek)
                .setParameter(2, startOfNextWeek)
                .getResultList();
    }


    /**
     * Lấy top sản phẩm bán chạy
     * @param limit Số lượng sản phẩm muốn lấy (top N)
     * @param startDate Ngày bắt đầu
     * @param endDate Ngày kết thúc
     * @return Danh sách top sản phẩm
     */
    public List<TopProductDTO> getTopProducts(int limit, Date startDate, Date endDate) {
        String jpql = "SELECT p.id, p.productName, " +
                "SUM(oi.quantity) as totalQuantity, " +
                "SUM(oi.subTotal) as totalRevenue " +
                "FROM WholesaleOrderItem oi " +
                "JOIN oi.product p " +
                "JOIN oi.order o " +
                "WHERE o.createdAt >= :startDate " +
                "AND o.createdAt < :endDate " +
                "AND o.status = 'SHIPPED' " +
                "GROUP BY p.id, p.productName " +
                "ORDER BY totalQuantity DESC";

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
            Long totalQuantity = (Long) row[2];
            BigDecimal totalRevenue = (BigDecimal) row[3];

            TopProductDTO dto = new TopProductDTO(
                    productId,
                    productName,
                    totalQuantity.intValue(),
                    totalRevenue,
                    null, // imageUrl có thể thêm sau
                    rank++
            );
            topProducts.add(dto);
        }

        return topProducts;
    }

    /**
     * Lấy top sản phẩm bán chạy hôm nay
     */
    public List<TopProductDTO> getTopProductsToday(int limit) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfDay = cal.getTime();

        cal.add(Calendar.DATE, 1);
        Date endOfDay = cal.getTime();

        return getTopProducts(limit, startOfDay, endOfDay);
    }

    /**
     * Lấy top sản phẩm bán chạy tuần này
     */
    public List<TopProductDTO> getTopProductsThisWeek(int limit) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfWeek = cal.getTime();

        cal.add(Calendar.WEEK_OF_YEAR, 1);
        Date endOfWeek = cal.getTime();

        return getTopProducts(limit, startOfWeek, endOfWeek);
    }

    /**
     * Lấy top sản phẩm bán chạy tháng này
     */
    public List<TopProductDTO> getTopProductsThisMonth(int limit) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfMonth = cal.getTime();

        cal.add(Calendar.MONTH, 1);
        Date endOfMonth = cal.getTime();

        return getTopProducts(limit, startOfMonth, endOfMonth);
    }

    public int getTotalSoldQuantity() {
        String jpql = "SELECT COALESCE(SUM(oi.quantity), 0) FROM WholesaleOrderItem oi " +
                "JOIN oi.order o " +
                "WHERE o.status = 'SHIPPED'";
        Long totalSold = em.createQuery(jpql, Long.class).getSingleResult();
        return totalSold != null ? totalSold.intValue() : 0;
    }

}



