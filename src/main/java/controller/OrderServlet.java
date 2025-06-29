package controller;

import com.google.gson.Gson;
import dto.OrderCheckRequestDTO;
import dto.OrderCustomerDTO;
import dto.OrderResponseDTO;
import dto.PlaceOrderRequestDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.WholesaleOrderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/api/orders/*")
public class OrderServlet extends HttpServlet {
    private final WholesaleOrderService wholesaleOrderService = new WholesaleOrderService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Kỳ vọng URL: /api/orders/{orderId}
        String path = req.getPathInfo(); // ví dụ "/123"
        if (path == null || path.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Chưa chỉ định orderId");
            return;
        }

        String[] parts = path.split("/");
        if (parts.length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL không hợp lệ");
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "orderId phải là số");
            return;
        }

        try {
            OrderCustomerDTO dto = wholesaleOrderService.getOrderForCustomer(orderId);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write(gson.toJson(dto));
        } catch (IllegalArgumentException ex) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        Integer customerId = (session != null) ? (Integer) session.getAttribute("accountId") : null;
        if (customerId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(gson.toJson(
                    new MessageResponse("Bạn chưa đăng nhập!", false)
            ));
            return;
        }

        OrderCheckRequestDTO orderReq;

        try (BufferedReader reader = request.getReader()) {
            orderReq = gson.fromJson(reader, OrderCheckRequestDTO.class);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(
                    new OrderResponseDTO(false, "Dữ liệu đầu vào không hợp lệ", null, null, null)
            ));
            return;
        }

        try {
            // Chuyển deliveryDate từ String sang LocalDateTime nếu cần
            LocalDateTime deliveryDate = LocalDateTime.parse(orderReq.getDeliveryDate());

            OrderResponseDTO orderResp = wholesaleOrderService.placeOrder(
                    customerId,
                    deliveryDate,
                    orderReq.getAddressId(),
                    40 // tốc độ km/h giả định
            );

            // 4. Trả kết quả
            if (orderResp.isSuccess()) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            response.getWriter().write(gson.toJson(orderResp));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(
                    new OrderResponseDTO(false, "Lỗi hệ thống: " + e.getMessage(), null, null, null)
            ));
        }
    }
}
