package controller;

import com.google.gson.Gson;
import dto.TopProductDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.StatsService;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/top-products")
public class TopProductServlet extends HttpServlet {
    
    private final StatsService statsService = new StatsService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession(false);
//        if (session == null || session.getAttribute("accountId") == null) {
//            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn chưa đăng nhập");
//            return;
//        }

        try {
            // Lấy parameters
            String period = req.getParameter("period"); // today, week, month
            String limitStr = req.getParameter("limit"); // số lượng sản phẩm muốn lấy
            String sortBy = req.getParameter("sortBy"); // quantity | revenue
            
            // Mặc định
            int limit = 10; // mặc định lấy top 10
            if (limitStr != null && !limitStr.trim().isEmpty()) {
                try {
                    limit = Integer.parseInt(limitStr);
                    if (limit <= 0 || limit > 100) {
                        limit = 10; // giới hạn tối đa 100
                    }
                } catch (NumberFormatException e) {
                    limit = 10; // nếu parse lỗi thì dùng mặc định
                }
            }

            List<TopProductDTO> topProducts;
            
            if (period != null && !period.trim().isEmpty()) {
                // Lấy theo thời gian cụ thể
                topProducts = statsService.getTopProductsByPeriod(period, limit, sortBy);
            }
                else {
                // Mặc định lấy top sản phẩm tháng này
                topProducts = statsService.getTopProductsThisMonth(limit, sortBy);
            }
            
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write(gson.toJson(topProducts));
            
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
} 