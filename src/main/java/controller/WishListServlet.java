package controller;

import com.google.gson.Gson;
import dto.WishListDTO;
import entity.Wishlist;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.WishlistService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/wishlist/*")
public class WishListServlet extends HttpServlet {
    private final WishlistService wishlistService = new WishlistService();
    private final Gson gson = new Gson();

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
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Dữ liệu không hợp lệ");
            return;
        }

        try {
            List<WishListDTO> wishList = wishlistService.getWishlistByCustomerId(userId.intValue());

            resp.setContentType("application/json;charset=UTF-8");
            gson.toJson(wishList, resp.getWriter());
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        BufferedReader reader = req.getReader();
        WishListDTO dto = gson.fromJson(reader, WishListDTO.class);


        try {
            wishlistService.addToWishlist(dto);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"message\":\"Sản phẩm đã được thêm vào wishlist\",\"success\":true}");

        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\",\"success\":false}");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID sản phẩm là bắt buộc");
            return;
        }
        try {
            int productId = Integer.parseInt(pathInfo.substring(1));

            wishlistService.removeFromWishlist(userId.intValue(), productId);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"message\":\"Sản phẩm được xóa thành công\",\"success\":true}");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\",\"success\":false}");
        }
    }
}
