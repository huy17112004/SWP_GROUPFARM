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
import service.ProductService;

import java.io.IOException;

@WebServlet("/api/product-detail/*")
public class ProductDetailServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo(); // /6

        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Missing product ID in URL.\"}");
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
                response.getWriter().write("{\"error\":\"Product not found.\"}");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid product ID.\"}");
        }
    }
}