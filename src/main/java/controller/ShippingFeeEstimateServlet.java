package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.OrderCheckRequestDTO;
import service.ShippingService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.LocalDateTimeAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

    @WebServlet("/api/shipping/estimate")
public class ShippingFeeEstimateServlet extends HttpServlet {

    private final ShippingService shippingService = new ShippingService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Lấy userId từ session
            Integer userId = (Integer) req.getSession().getAttribute("userId");
            if (userId == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("Chưa đăng nhập.");
                return;
            }

            int addressId = (int) Integer.parseInt(req.getParameter("addressId"));

            // Tính phí vận chuyển
            double avgSpeedKmph = 50.0;
            BigDecimal shippingFee = shippingService.calculateEstimateShippingFee(userId, addressId, LocalDateTime.parse("2025-12-30T14:30:00"), avgSpeedKmph);

            // Trả kết quả JSON về client
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(shippingFee));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error calculating shipping fee: " + e.getMessage());
        }
    }
}
