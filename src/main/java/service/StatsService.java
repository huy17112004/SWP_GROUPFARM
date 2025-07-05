package service;

import dao.StatsDAO;
import dao.WholesaleOrderDAO;
import dto.RevenueDTO;
import dto.ShippingStatsDTO;
import dto.StatsDTO;
import dto.TopProductDTO;
import entity.WholesaleOrder;
import jakarta.persistence.EntityManager;
import util.JpaUtil;
import java.math.BigDecimal;
import java.util.List;

public class StatsService {


    public RevenueDTO getRevenueStats() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StatsDAO statsDAO = new StatsDAO(em);

            BigDecimal todayRevenue = statsDAO.getTodayRevenue();
            BigDecimal weekRevenue = statsDAO.getWeekRevenue();
            BigDecimal monthRevenue = statsDAO.getMonthRevenue();
            BigDecimal yearRevenue = statsDAO.getYearRevenue();
            return new RevenueDTO(todayRevenue, weekRevenue, monthRevenue, yearRevenue);

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    } 

    // lấy doanh thu theo thời gian cụ thể
    public RevenueDTO getRevenueByPeriod(String period) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StatsDAO statsDAO = new StatsDAO(em);
            RevenueDTO dto = new RevenueDTO();

            switch (period.toLowerCase()) {
                case "today":
                    dto.setTodayRevenue(statsDAO.getTodayRevenue());
                    dto.setPeriod("today");
                    break;
                case "week":
                    dto.setWeekRevenue(statsDAO.getWeekRevenue());
                    dto.setPeriod("week");
                    break;
                case "month":
                    dto.setMonthRevenue(statsDAO.getMonthRevenue());
                    dto.setPeriod("month");
                    break;
                case "year":
                    dto.setYearRevenue(statsDAO.getYearRevenue());
                    dto.setPeriod("year");
                    break;
                default:
                    // Trả về tất cả
                    dto = new RevenueDTO(
                            statsDAO.getTodayRevenue(),
                            statsDAO.getWeekRevenue(),
                            statsDAO.getMonthRevenue(),
                            statsDAO.getYearRevenue()
                    );
                    dto.setPeriod("all");
            }
            return dto;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }


    public StatsDTO getOrdersToday() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StatsDAO statsDAO = new StatsDAO(em);
            int totalOrder = statsDAO.getWholesaleOrdersToday();
            return new StatsDTO(totalOrder);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public ShippingStatsDTO getShippingStatsToday() { // kiểm tra trạng thái đơn hàng
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StatsDAO statsDAO = new StatsDAO(em);
            return statsDAO.getShippingStatsToday();
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // sản phẩm bán chạy hôm nay
    public List<TopProductDTO> getTopProductsToday(int limit) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StatsDAO statsDAO = new StatsDAO(em);
            return statsDAO.getTopProductsToday(limit);
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

// sản phẩm bán chạy hàng tuần
    public List<TopProductDTO> getTopProductsThisWeek(int limit) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StatsDAO statsDAO = new StatsDAO(em);
            return statsDAO.getTopProductsThisWeek(limit);
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // sản phẩm bán chạy tháng
    public List<TopProductDTO> getTopProductsThisMonth(int limit) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StatsDAO statsDAO = new StatsDAO(em);
            return statsDAO.getTopProductsThisMonth(limit);
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // lấy sản phẩm bán chạy tuùy chỉnh theo thời gian
    public List<TopProductDTO> getTopProductsByPeriod(String period, int limit) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StatsDAO statsDAO = new StatsDAO(em);
            
            switch (period.toLowerCase()) {
                case "today":
                    return statsDAO.getTopProductsToday(limit);
                case "week":
                    return statsDAO.getTopProductsThisWeek(limit);
                case "month":
                    return statsDAO.getTopProductsThisMonth(limit);
                default:
                    // Mặc định lấy top sản phẩm tháng này
                    return statsDAO.getTopProductsThisMonth(limit);
            }
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }
}

