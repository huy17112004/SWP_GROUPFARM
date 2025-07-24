package service;

import dao.ProductDAO;
import dao.StatsDAO;
import dto.ShippingOrderDTO;
import dto.RevenueDTO;
import dto.StatsDTO;
import dto.TopProductDTO;
import entity.Product;
import jakarta.persistence.EntityManager;
import util.JpaUtil;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class  StatsService {


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


        public List<ShippingOrderDTO> getAllPendingOrShippedOrders() {
            EntityManager em = JpaUtil.getEntityManager();
            try {
                StatsDAO dao = new StatsDAO(em);
                return dao.getAllPendingOrShippedOrders();
            } finally {
                em.close();
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



    // sản phẩm bán chạy hôm nay
    public List<TopProductDTO> getTopProductsToday(int limit, String sortBy) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StatsDAO statsDAO = new StatsDAO(em);
            return statsDAO.getTopProductsToday(limit, sortBy);
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // sản phẩm bán chạy hàng tuần
    public List<TopProductDTO> getTopProductsThisWeek(int limit, String sortBy) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StatsDAO statsDAO = new StatsDAO(em);
            return statsDAO.getTopProductsThisWeek(limit, sortBy);
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // sản phẩm bán chạy tháng
    public List<TopProductDTO> getTopProductsThisMonth(int limit, String sortBy) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StatsDAO statsDAO = new StatsDAO(em);
            return statsDAO.getTopProductsThisMonth(limit, sortBy);
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // lấy sản phẩm bán chạy tùy chỉnh theo thời gian
    public List<TopProductDTO> getTopProductsByPeriod(String period, int limit, String sortBy) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StatsDAO statsDAO = new StatsDAO(em);
            switch (period.toLowerCase()) {
                case "today":
                    return statsDAO.getTopProductsToday(limit, sortBy);
                case "week":
                    return statsDAO.getTopProductsThisWeek(limit, sortBy);
                case "month":
                    return statsDAO.getTopProductsThisMonth(limit, sortBy);
                default:
                    return statsDAO.getTopProductsThisMonth(limit, sortBy);
            }
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }
    public List<Product> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            ProductDAO productDAO = new ProductDAO(em);
            return productDAO.findAll();
        } finally {
            em.close();
        }
    }

    public int countAllProducts() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            ProductDAO dao = new ProductDAO(em);
            return dao.totalProducts();
        } finally {
            em.close();
        }
    }

    public int getTotalSoldQuantity() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StatsDAO statsDAO = new StatsDAO(em);
            return statsDAO.getTotalSoldQuantity();
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // Lấy top sản phẩm bán chạy theo thời gian và kiểu sắp xếp
    public List<TopProductDTO> getTopProducts(String period, int limit, String sortBy) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StatsDAO statsDAO = new StatsDAO(em);
            if (period == null || period.isEmpty()) period = "month";
            if (sortBy == null || sortBy.isEmpty()) sortBy = "quantity";
            switch (period.toLowerCase()) {
                case "today":
                    return statsDAO.getTopProductsToday(limit, sortBy);
                case "week":
                    return statsDAO.getTopProductsThisWeek(limit, sortBy);
                case "month":
                default:
                    return statsDAO.getTopProductsThisMonth(limit, sortBy);
            }
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }
}

