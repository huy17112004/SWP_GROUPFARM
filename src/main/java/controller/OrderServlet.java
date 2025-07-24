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
            // Get all orders for current customer
            HttpSession session = req.getSession(false);
            Integer customerId = (session != null) ? (Integer) session.getAttribute("userId") : null;
            if (customerId == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.setContentType("application/json;charset=UTF-8");
                resp.getWriter().write(gson.toJson(new MessageResponse("Bạn chưa đăng nhập!", false)));
                return;
            }
            try {
                var orders = wholesaleOrderService.getAllOrdersForCustomer(customerId);
                resp.setContentType("application/json;charset=UTF-8");
                resp.getWriter().write(gson.toJson(orders));
            } catch (Exception ex) {
                ex.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server");
            }
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
        Integer customerId = (session != null) ? (Integer) session.getAttribute("userId") : null;
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        HttpSession session = req.getSession(false);
        Integer customerId = (session != null) ? (Integer) session.getAttribute("userId") : null;
        if (customerId == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write(gson.toJson(new MessageResponse("Bạn chưa đăng nhập!", false)));
            return;
        }

        // Kỳ vọng URL: /api/orders/{orderId}/confirm
        String path = req.getPathInfo(); // ví dụ "/123/confirm"
        if (path == null || path.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL không hợp lệ");
            return;
        }

        String[] parts = path.split("/");
        if (parts.length != 3 || !"confirm".equals(parts[2])) {
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
            boolean success = wholesaleOrderService.confirmOrder(orderId, customerId);
            if (success) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(gson.toJson(new MessageResponse("Xác nhận đơn hàng thành công!", true)));
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson(new MessageResponse("Không thể xác nhận đơn hàng!", false)));
            }
        } catch (IllegalArgumentException | IllegalStateException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(new MessageResponse(ex.getMessage(), false)));
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(new MessageResponse("Lỗi server: " + ex.getMessage(), false)));
        }
    }

    // Helper class for error messages
    private static class MessageResponse {
        private String message;
        private boolean success;

        public MessageResponse(String message, boolean success) {
            this.message = message;
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }
}
