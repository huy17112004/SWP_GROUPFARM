package controller;

import com.google.gson.Gson;
import dto.SellerDTO;
import dto.SellerRequestDTO;
import service.SellerService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/seller/*")
public class SellerApiServlet extends HttpServlet {
    private final SellerService svc = new SellerService();
    private final Gson gson = new Gson();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json; charset=UTF-8");
        String path = req.getPathInfo();
        if (path == null || path.equals("/") || path.isEmpty()) {
            // Lấy danh sách Seller
            List<SellerDTO> sellers = svc.getAll();
            resp.getWriter().write(gson.toJson(sellers));
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\": \"API không tồn tại!\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json; charset=UTF-8");
        String path = req.getPathInfo();
        try {
            if (path == null || path.equals("/") || path.isEmpty() || path.equals("/create")) {
                // Tạo Seller
                SellerRequestDTO dto = gson.fromJson(req.getReader(), SellerRequestDTO.class);
                svc.create(dto);
                resp.getWriter().write("{\"message\": \"Tạo tài khoản người bán thành công!\"}");
            } else if (path.startsWith("/update")) {
                int id = Integer.parseInt(req.getParameter("id"));
                SellerRequestDTO dto = gson.fromJson(req.getReader(), SellerRequestDTO.class);
                svc.update(id, dto);
                resp.getWriter().write("{\"message\": \"Cập nhật thành công!\"}");
            } else if (path.startsWith("/delete")) {
                int id = Integer.parseInt(req.getParameter("id"));
                svc.delete(id);
                resp.getWriter().write("{\"message\": \"Đã xóa (soft delete) thành công!\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"API không tồn tại!\"}");
            }
        } catch (IllegalArgumentException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
        }
    }
}
