package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.OrderSellerDTO;
import entity.WholesaleOrder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.SellerOrderService;
import service.WholesaleOrderService;
import util.LocalDateTimeAdapter;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/api/order-detail")
public class OrderDetailServlet extends HttpServlet {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderIdStr = request.getParameter("orderId");
        try {
            int orderId = Integer.parseInt(orderIdStr);
            SellerOrderService  sellerOrderService = new SellerOrderService();
            OrderSellerDTO dto = sellerOrderService.getByOrderId(orderId);
            response.getWriter().write(gson.toJson(dto));
        } catch (NumberFormatException e) {
        }
    }
}
