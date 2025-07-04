package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.DealRequestCustomerDTO;
import service.DealRequestService;
import util.LocalDateTimeAdapter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/deal-requests/orders/*")
public class CustomerDealServlet extends HttpServlet {
    private final DealRequestService service = new DealRequestService();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1. Lấy orderId từ URL: /api/orders/123/deal-requests
        String path = req.getPathInfo(); // "/123/deal-requests"
        String[] parts = path.split("/");
        int orderId = Integer.parseInt(parts[1]);

        // 2. Lấy customerId từ session (AuthFilter đã đảm bảo session & role)
        int customerId = (Integer) req.getSession().getAttribute("accountId");

        // 3. Gọi service
        List<DealRequestCustomerDTO> deals = service.listDealsForOrder(orderId, customerId);

        // 4. Trả về JSON
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(gson.toJson(deals));
    }
}
