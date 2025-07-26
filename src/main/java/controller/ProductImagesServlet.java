package controller;

import com.google.gson.Gson;
import entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/api/product-images")
public class ProductImagesServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();

        try {
            String productIdParam = request.getParameter("productId");
            if (productIdParam == null || productIdParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Product ID là bắt buộc.\"}");
                return;
            }

            int productId = Integer.parseInt(productIdParam);
            Product product = productService.findById(productId);

            if (product == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Sản phẩm không tìm thấy.\"}");
                return;
            }

            // Lấy danh sách URL ảnh
            List<String> imageUrls = product.getImages() != null
                    ? product.getImages().stream()
                            .map(img -> img.getImageUrl())
                            .collect(Collectors.toList())
                    : List.of();

            response.getWriter().write(gson.toJson(imageUrls));

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid product ID format.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Internal server error.\"}");
        }
    }
} 