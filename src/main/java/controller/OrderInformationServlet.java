package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.OrderInformationRequestDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.WholesaleOrderService;
import util.LocalDateTimeAdapter;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/order-information/*")
public class OrderInformationServlet extends HttpServlet {
    private final WholesaleOrderService orderService = new WholesaleOrderService();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1. Lấy orderId từ URL: /api/orders/123/deal-requests
        String path = req.getPathInfo(); // "/123/deal-requests"
        String[] parts = path.split("/");
        int orderId = Integer.parseInt(parts[1]);

        // 3. Gọi service
        OrderInformationRequestDTO order = orderService.getOrderInformation(orderId);

        // 4. Trả về JSON
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(gson.toJson(order));
    }

}
