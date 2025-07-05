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
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/api/product")
public class ProductServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy tham số tìm kiếm
        String categoryIdParam = request.getParameter("categoryId");
        String nameParam = request.getParameter("name");
        String sortType = request.getParameter("sortType");
        String pageParam = request.getParameter("page");
        String sizeParam = request.getParameter("size");

        Integer categoryId = null;
        if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
            categoryId = Integer.parseInt(categoryIdParam);
        }

        int page = 0;   // mặc định trang 0 (trang đầu)
        int size = 10;  // mặc định 10 sản phẩm mỗi trang

        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 0) page = 0; // tránh số âm
            } catch (NumberFormatException ignored) {}
        }

        if (sizeParam != null && !sizeParam.isEmpty()) {
            try {
                size = Integer.parseInt(sizeParam);
                if (size <= 0) size = 10; // tránh size <= 0
            } catch (NumberFormatException ignored) {}
        }

        List<ProductResponseDTO> dtoList = productService.searchProducts(categoryId, nameParam, sortType, page, size);

        long totalItems = productService.countProducts(categoryId, nameParam);
        int totalPages = (int) Math.ceil((double) totalItems / size);

        Gson gson = new Gson();
        HashMap<String, Object> map = new HashMap<>();
        map.put("products", dtoList);
        map.put("totalPages", totalPages);
        map.put("totalItems", totalItems);
        String json = gson.toJson(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }

}
