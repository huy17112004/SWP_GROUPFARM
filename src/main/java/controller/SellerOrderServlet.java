package controller;

import com.google.gson.Gson;
import dto.OrderSellerDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.SellerOrderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/seller/orders")
public class SellerOrderServlet extends HttpServlet {
    private final SellerOrderService sellerOrderService = new SellerOrderService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // Check if user is logged in and is a seller
        HttpSession session = req.getSession(false);
        Integer sellerId = (session != null) ? (Integer) session.getAttribute("accountId") : null;
        
        if (sellerId == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write(gson.toJson(new MessageResponse("Bạn chưa đăng nhập!", false)));
            return;
        }

        try {
            List<OrderSellerDTO> orders = sellerOrderService.getAllOrdersForSeller(sellerId);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write(gson.toJson(orders));
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // Check if user is logged in and is a seller
        HttpSession session = req.getSession(false);
        Integer sellerId = (session != null) ? (Integer) session.getAttribute("accountId") : null;
        
        if (sellerId == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write(gson.toJson(new MessageResponse("Bạn chưa đăng nhập!", false)));
            return;
        }

        try {
            // Read request body
            BufferedReader reader = req.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            
            OrderActionRequest request = gson.fromJson(sb.toString(), OrderActionRequest.class);
            
            if (request.getOrderId() == null || request.getAction() == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setContentType("application/json;charset=UTF-8");
                resp.getWriter().write(gson.toJson(new MessageResponse("Thiếu thông tin orderId hoặc action!", false)));
                return;
            }

            // Process the action
            boolean success = sellerOrderService.processOrderAction(sellerId, request.getOrderId(), request.getAction());
            
            if (success) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application/json;charset=UTF-8");
                resp.getWriter().write(gson.toJson(new MessageResponse("Xử lý đơn hàng thành công!", true)));
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setContentType("application/json;charset=UTF-8");
                resp.getWriter().write(gson.toJson(new MessageResponse("Không thể xử lý đơn hàng!", false)));
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json;charset=UTF-8");
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

    // Helper class for order action request
    private static class OrderActionRequest {
        private Integer orderId;
        private String action; // "ACCEPT" or "REJECT"

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
} 