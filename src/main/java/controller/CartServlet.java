package controller;
import com.google.gson.Gson;
import dto.CartItemDTO;
import entity.Cart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.CartService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/cart/*")
public class CartServlet extends HttpServlet {

    private final CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("accountId") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn chưa đăng nhập");
            return;
        }

        Object accountId = session.getAttribute("accountId");
        Long userId;
        if (accountId instanceof Integer) {
            userId = ((Integer) accountId).longValue();
        } else if (accountId instanceof Long) {
            userId = (Long) accountId;
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid session data");
            return;
        }

        try {
            List<CartItemDTO> cartItems = cartService.getCartByUserId(userId);
            // Trả JSON
            resp.setContentType("application/json;charset=UTF-8");
            new Gson().toJson(cartItems, resp.getWriter());

        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        BufferedReader reader = req.getReader();
        CartItemDTO dto = gson.fromJson(reader, CartItemDTO.class);

        HttpSession session = req.getSession(false);
        if(session == null|| session.getAttribute("accountId") == null){
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn chưa đăng nhập");
            return;
        }

        Object accountId = session.getAttribute("accountId");
        Long userId;
        if (accountId instanceof Integer) {
            userId = ((Integer) accountId).longValue();
        } else if (accountId instanceof Long) {
            userId = (Long) accountId;
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Dữ liệu không hợp lệ");
            return;
        }

        try {
            Cart cart = cartService.addToCart(dto);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"message\":\"Item added to cart successfully\",\"success\":true}");

        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\",\"success\":false}");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        HttpSession session  = req.getSession();

        if (session == null || session.getAttribute("accountId") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn chưa đăng nhập");
            return;
        }

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID sản phẩm là bắt buộc");
            return;
        }
        try {
            Long productId = Long.parseLong(pathInfo.substring(1));

            Object accountIdObj = session.getAttribute("accountId");
            long userId;
            if (accountIdObj instanceof Integer) {
                userId = ((Integer) accountIdObj).longValue();
            } else if (accountIdObj instanceof Long) {
                userId = (Long) accountIdObj;
            } else {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Dữ liệu không hợp lệ");
                return;
            }
            cartService.removeFromCart(productId, userId);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"message\":\"Sản phẩm được xóa thành công\",\"success\":true}");

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID sản phẩm không hợp lệ");
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\",\"success\":false}");
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("accountId") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn chưa đăng nhập");
            return;
        }

        Object accountId = session.getAttribute("accountId");
        Long userId;
        if (accountId instanceof Integer) {
            userId = ((Integer) accountId).longValue();
        } else if (accountId instanceof Long) {
            userId = (Long) accountId;
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Dữ liệu không hợp lệ");
            return;
        }

        Gson gson = new Gson();
        CartItemDTO dto = gson.fromJson(req.getReader(), CartItemDTO.class);
        
        try {
            boolean updated = cartService.updateQuantity(userId, dto.getProductId(), dto.getQuantity());

            resp.setContentType("application/json;charset=UTF-8");
            if (updated) {
                resp.getWriter().write("{\"message\":\"Cập nhật thành công\",\"success\":true}");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"message\":\"Không thể cập nhật\",\"success\":false}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\":\"Lỗi server: " + e.getMessage() + "\",\"success\":false}");
        }
    }

}
