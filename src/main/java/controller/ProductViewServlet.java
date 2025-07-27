package controller;

import com.google.gson.Gson;
import dto.ProductCreateDTO;
import dto.ProductViewDTO;
import dto.ProductViewDTO;
import entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ProductService;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/api/product-view/*")
public class ProductViewServlet extends HttpServlet {

    private final ProductService productService = new ProductService();
    private final Gson gson = new Gson();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

        String path = req.getPathInfo(); // null, "/", hoặc "/{id}"
        if (path != null && path.length() > 1) {
            // --- GET detail ---
            try {
                int id = Integer.parseInt(path.substring(1));
                Product p = productService.findById(id);
                if (p == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write(new Gson().toJson(Map.of("error", "Product not found: " + id)));
                } else {
                    resp.getWriter().write(new Gson().toJson(new ProductViewDTO(p)));
                }
            } catch (NumberFormatException ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(new Gson().toJson(Map.of("error", "Invalid product ID format")));
            }
            return;
        }

        // --- GET list (không có id) ---
        // Lấy tham số tìm kiếm / phân trang
        String categoryIdParam = req.getParameter("categoryId");
        String nameParam       = req.getParameter("name");
        String sortType        = req.getParameter("sortType");
        String pageParam       = req.getParameter("page");
        String sizeParam       = req.getParameter("size");

        Integer categoryId = null;
        if (categoryIdParam != null && !categoryIdParam.isBlank()) {
            categoryId = Integer.parseInt(categoryIdParam);
        }

        int page = 0, size = 1000;
        try { if (pageParam  != null) page = Math.max(0, Integer.parseInt(pageParam)); } catch(Exception ignored){}
        try { if (sizeParam  != null) size = Math.max(1, Integer.parseInt(sizeParam)); } catch(Exception ignored){}

        List<ProductViewDTO> dtoList = productService.searchProductHaveEntryPrice(categoryId, nameParam, sortType, page, size);
        long totalItems = productService.countProducts(categoryId, nameParam);
        int totalPages = (int) Math.ceil((double) totalItems / size);

        resp.getWriter().write(new Gson().toJson(dtoList));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin",  "*");
        resp.setContentType("application/json;charset=UTF-8");

//        // 1. Kiểm tra quyền ADMIN
//        HttpSession session = req.getSession(false);
//        if (session == null || !"ADMIN".equals(session.getAttribute("userRole"))) {
//            resp.setStatus(session == null ? HttpServletResponse.SC_UNAUTHORIZED
//                    : HttpServletResponse.SC_FORBIDDEN);
//            resp.getWriter().write(gson.toJson(
//                    Map.of("error", session == null ? "Unauthorized" : "Forbidden: Admin only")
//            ));
//            return;
//        }

        // 2. Đọc DTO từ body
        ProductCreateDTO dto = gson.fromJson(req.getReader(), ProductCreateDTO.class);

        try {
            // 3. Tạo Product với hình
            Product created = productService.createProduct(dto);

            // 4. Chuyển sang ResponseDTO
            ProductViewDTO out = new ProductViewDTO(created);

            // 5. Trả về client
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(gson.toJson(out));
        } catch (IllegalArgumentException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(Map.of("error", ex.getMessage())));
        } catch (Exception ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write(gson.toJson(Map.of(
                    "error",   "InternalServerError",
                    "message", ex.getMessage(),
                    "stack",   sw.toString()
            )));
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(Map.of("error", "Missing product ID in URL")));
            return;
        }

        try {
            int id = Integer.parseInt(path.substring(1));
            ProductCreateDTO dto = gson.fromJson(req.getReader(), ProductCreateDTO.class);
            Product updated = productService.updateProduct(id, dto);
            resp.getWriter().write(gson.toJson(new ProductViewDTO(updated)));
        } catch (NumberFormatException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(Map.of("error", "Invalid ID format")));
        } catch (IllegalArgumentException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(Map.of("error", ex.getMessage())));
        } catch (Exception ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            resp.getWriter().write(gson.toJson(Map.of(
                    "error",   "InternalServerError",
                    "message", ex.getMessage(),
                    "stack",   sw.toString()
            )));
        }
    }

    // DELETE /api/product/{id}   => xóa
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        String path = req.getPathInfo();
        System.out.println("DEBUG doDelete pathInfo: " + path);
        if (path == null || path.equals("/") || path.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(Map.of("error", "Missing product ID in URL")));
            return;
        }

        try {
            int id = Integer.parseInt(path.substring(1));
            productService.deleteProduct(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(Map.of("error", "Invalid ID format")));
        } catch (IllegalArgumentException ex) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write(gson.toJson(Map.of("error", ex.getMessage())));
        } catch (Exception ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            resp.getWriter().write(gson.toJson(Map.of(
                    "error",   "InternalServerError",
                    "message", ex.getMessage(),
                    "stack",   sw.toString()
            )));
        }
    }
}
