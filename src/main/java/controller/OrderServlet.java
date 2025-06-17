package controller;

import com.google.gson.Gson;
import dto.PlaceOrderRequestDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.WholesaleOrderService;

import java.io.IOException;

@WebServlet("/api/orders")
public class OrderServlet extends HttpServlet {
    private final WholesaleOrderService wholesaleOrderService = new WholesaleOrderService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("accountId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(gson.toJson(new MessageResponse("Chưa đăng nhập", false)));
            return;
        }

        int customerId = (Integer) session.getAttribute("accountId");
        PlaceOrderRequestDTO placeOrderRequestDTO = gson.fromJson(request.getReader(), PlaceOrderRequestDTO.class);
        placeOrderRequestDTO.setCustomerId(customerId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int orderId = wholesaleOrderService.placeOrder(placeOrderRequestDTO);
            MessageResponse messageResponse = new MessageResponse("Đặt hàng thành công", true, orderId);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(messageResponse));
        } catch (Exception e) {
            MessageResponse messageResponse = new MessageResponse("Đặt hàng thất bại", false);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(messageResponse));
        }
    }
}
