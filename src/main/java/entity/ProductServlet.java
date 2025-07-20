package entity;

import com.google.gson.Gson;
import dto.ProductCreateDTO;
import dto.ProductResponseDTO;
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
import java.util.List;
import java.util.Map;

@WebServlet("/api/product/*")
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
