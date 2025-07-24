package controller;

import com.google.gson.Gson;
import dto.ProductDetailDTO;
import dto.ProductResponseDTO;
import entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.ProductService;

import java.io.IOException;

@WebServlet("/api/product-detail/*")
public class ProductDetailServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("accountId") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn chưa đăng nhập");
            return;
        }


        Object accountId = session.getAttribute("accountId");
        if (!(accountId instanceof Integer || accountId instanceof Long)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Dữ liệu không hợp lệ");
            return;
        }
        String pathInfo = request.getPathInfo(); //

        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"ID sản phẩm là bắt buộc.\"}");
                return;
            }

            // Cắt bỏ dấu "/" để lấy id
            int productId = Integer.parseInt(pathInfo.substring(1));
            Product product = productService.findById(productId);

            if (product != null) {
                ProductDetailDTO dto = new ProductDetailDTO(product);
                response.getWriter().write(gson.toJson(dto));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Sản phẩm không tìm thấy .\"}");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid product ID.\"}");
        }
    }
}