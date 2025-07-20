package controller;

import com.google.gson.Gson;
import dto.ProductDashboardDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.ProductDashboardService;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/products-dashboard/*")
public class ProductDashBoardServlet extends HttpServlet {

    private final ProductDashboardService productService = new ProductDashboardService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        //Kiểm tra session bắt buộc phải có accountId
//        HttpSession session = req.getSession(false); // false: không tạo mới nếu không có
//        if (session == null || session.getAttribute("accountId") == null) {
//            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
//            resp.setContentType("application/json;charset=UTF-8");
//            resp.getWriter().write("{\"error\":\"Bạn chưa đăng nhập\"}");
//            return;
//        }
        HttpSession session = req.getSession(true);
        session.setAttribute("accountId", 1); // Giả lập user có ID = 1

        try {
            List<ProductDashboardDTO> productList = productService.getAllDashboardProducts();
            resp.setContentType("application/json;charset=UTF-8");
            gson.toJson(productList, resp.getWriter());
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //  Giả lập đăng nhập nếu chưa có
        HttpSession session = req.getSession(true);
        session.setAttribute("accountId", 1); // giả lập user ID = 1

        String pathInfo = req.getPathInfo(); // "/5"

        //  Kiểm tra đường dẫn có chứa ID không
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"error\": \"Thiếu Product ID để xóa\", \"success\": false}");
            return;
        }

        try {
            Long productId = Long.parseLong(pathInfo.substring(1)); // bỏ dấu "/"

            String message = productService.deleteProduct(productId); // gọi service
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"message\": \"" + message + "\", \"success\": true}");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"error\": \"Product ID không hợp lệ\", \"success\": false}");
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\", \"success\": false}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String pathInfo = req.getPathInfo(); // ví dụ "/5"

        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            ProductDashboardDTO requestDTO = gson.fromJson(req.getReader(), ProductDashboardDTO.class);
            ProductDashboardDTO updated = productService.updateProduct(id, requestDTO);

            if (updated == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"Không tìm thấy sản phẩm\"}");
            } else {
                gson.toJson(updated, resp.getWriter());
            }

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

}