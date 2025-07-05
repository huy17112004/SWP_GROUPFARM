package controller;

import com.google.gson.Gson;
import entity.Wishlist;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.WishlistService;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/wishlist/*")
public class WishListServlet extends HttpServlet {
    private final WishlistService wishlistService = new WishlistService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        Object accountId = (session != null) ? session.getAttribute("accountId") : null;
        if (accountId == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return;
        }
        int userId = (accountId instanceof Integer) ? (Integer) accountId : ((Long) accountId).intValue();

        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json;charset=UTF-8");
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // Lấy toàn bộ wishlist
                List<Wishlist> wishlist = wishlistService.getWishlistByCustomerId(userId);
                resp.getWriter().write(gson.toJson(wishlist));
            } else if (pathInfo.startsWith("/check/")) {
                // Kiểm tra sản phẩm có trong wishlist
                int productId = Integer.parseInt(pathInfo.substring(7));
                boolean inWishlist = wishlistService.isInWishlist(userId, productId);
                resp.getWriter().write("{\"inWishlist\":" + inWishlist + "}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession(false);
        Object accountId = (session != null) ? session.getAttribute("accountId") : null;
        if (accountId == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return;
        }
        int userId = (accountId instanceof Integer) ? (Integer) accountId : ((Long) accountId).intValue();

        try {
            int productId = Integer.parseInt(req.getParameter("productId"));
            wishlistService.addToWishlist(userId, productId);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"message\":\"Added to wishlist\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        Object accountId = (session != null) ? session.getAttribute("accountId") : null;
        if (accountId == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return;
        }
        int userId = (accountId instanceof Integer) ? (Integer) accountId : ((Long) accountId).intValue();

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required");
            return;
        }
        try {
            int productId = Integer.parseInt(pathInfo.substring(1));
            wishlistService.removeFromWishlist(userId, productId);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"message\":\"Removed from wishlist\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
