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

        HttpSession session = req.getSession(false);
        if(session == null|| session.getAttribute("accountID") == null){
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return;
        }

        Long userId = (Long) session.getAttribute("accountID");
        dto.setUserId(userId);
        try {

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
        HttpSession session  = req.getSession();

        if (session == null || session.getAttribute("accountID") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return;
        }

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Product ID is required");
            return;
        }
        try {
            Long productId = (long) Integer.parseInt(pathInfo.substring(1));
            long userId = (long) session.getAttribute("accountID");

            cartService.removeFromCart(productId, userId);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"message\":\"Item removed from cart successfully\"}");


        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

}
