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

@WebServlet("/api/cart/*")
public class CartServlet extends HttpServlet {

    private final CartService cartService = new CartService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        BufferedReader reader = req.getReader();
        CartItemDTO dto = gson.fromJson(reader, CartItemDTO.class);

        try {
            // Thêm sản phẩm vào giỏ hàng trong cơ sở dữ liệu
            Cart cart = cartService.addToCart(dto);
            CartItemDTO responseDTO = new CartItemDTO();
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write(gson.toJson(responseDTO));

        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String userIdParam = req.getParameter("userId");

        if (pathInfo == null || pathInfo.equals("/") || userIdParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID and User ID are required");
            return;
        }

        try {
            Long productId = (long) Integer.parseInt(pathInfo.substring(1));
            long userId = Long.parseLong(userIdParam);

            cartService.removeFromCart(productId, userId);
            resp.setContentType("application/json;charset=UTF-8");

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

}
