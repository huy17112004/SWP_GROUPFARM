package controller;

import com.google.gson.Gson;
import dao.StatsDAO;
import dto.RevenueDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.StatsService;

import java.io.IOException;

@WebServlet("/api/revenue")
public class RevenueServlet extends HttpServlet {
    
    private final StatsService statsService = new StatsService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession(false);
//        if (session == null || session.getAttribute("accountId") == null) {
//            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Admin not logged in");
//            return;
//        }

        HttpSession session = req.getSession(true);
        session.setAttribute("accountId", 1); // giả sử id 1 là admin

        try {
            // Lấy parameter period nếu có
            String period = req.getParameter("period");
            RevenueDTO dto;
            
            if (period != null && !period.trim().isEmpty()) {
                // Lấy doanh thu theo thời gian cụ thể
                dto = statsService.getRevenueByPeriod(period);
            } else {
                // Lấy tất cả doanh thu
                dto = statsService.getRevenueStats();
            }
            
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write(gson.toJson(dto));
            
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
} 