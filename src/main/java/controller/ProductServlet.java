package controller;

import com.google.gson.Gson;
import com.google.gson.Gson;
import dto.ProductCreateDTO;
import dto.ProductResponseDTO;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/api/product")
public class ProductServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
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
            ProductResponseDTO out = new ProductResponseDTO(created);

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



}