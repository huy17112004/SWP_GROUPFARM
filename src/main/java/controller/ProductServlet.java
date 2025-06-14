package controller;

import com.google.gson.Gson;
import dto.ProductResponseDTO;
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

@WebServlet("/api/product")
public class ProductServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy tham số tìm kiếm
        String categoryIdsParam = request.getParameter("categoryIds");
        String minPriceParam = request.getParameter("minPrice");
        String maxPriceParam = request.getParameter("maxPrice");
        String nameParam = request.getParameter("name");

        List<Integer> categoryIds = null;
        if (categoryIdsParam != null && !categoryIdsParam.isEmpty()) {
            categoryIds = java.util.Arrays.stream(categoryIdsParam.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(java.util.stream.Collectors.toList());
        }

        Double minPrice = null;
        if (minPriceParam != null && !minPriceParam.isEmpty()) {
            minPrice = Double.parseDouble(minPriceParam);
        }

        Double maxPrice = null;
        if (maxPriceParam != null && !maxPriceParam.isEmpty()) {
            maxPrice = Double.parseDouble(maxPriceParam);
        }

        List<Product> products = productService.searchProducts(categoryIds, minPrice, maxPrice, nameParam);
        List<ProductResponseDTO> dtoList = products.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String json = gson.toJson(dtoList);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }
}
