package controller;

import com.google.gson.Gson;
import dto.ProductCreateDTO;
import dto.ProductListRequestDTO;
import dto.ProductListResponseDTO;
import entity.WarehouseManager;
import entity.WarehouseStaff;
import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.ProductListService;
import util.JpaUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@WebServlet("/api/products-list/*")
public class ProductListServlet extends HttpServlet {
    private final ProductListService service = new ProductListService();
    private final Gson gson = new Gson();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin",  "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin",  "*");
        resp.setContentType("application/json;charset=UTF-8");
        String path = req.getPathInfo();

        if (path != null && path.length() > 1) {
            // Chi tiết
            int id = Integer.parseInt(path.substring(1));
            ProductListResponseDTO dto = service.getProductDTOById(id);
            if (dto == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            resp.getWriter().write(gson.toJson(dto));
        } else {
            // Danh sách
            String search = req.getParameter("search");
            String cat    = req.getParameter("categoryId");
            Integer categoryId = cat != null ? Integer.parseInt(cat) : null;

            List<ProductListResponseDTO> dtos = service.getAllProducts(search, categoryId);
            resp.getWriter().write(gson.toJson(dtos));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

//        // 1. Kiểm tra đăng nhập
//        HttpSession session = req.getSession(false);
//        Integer accountId = (session != null) ? (Integer) session.getAttribute("accountId") : null;
//        if (accountId == null) {
//            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            resp.getWriter().write("{\"error\":\"Chưa đăng nhập\"}");
//            return;
//        }
//
//        // 2. Kiểm tra quyền (Manager hoặc Staff)
//        EntityManager em = JpaUtil.getEntityManager();
//        try {
//            boolean isManager = em.find(WarehouseManager.class, accountId) != null;
//            boolean isStaff = em.find(WarehouseStaff.class, accountId) != null;
//            em.close();
//            if (!isManager && !isStaff) {
//                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                resp.getWriter().write("{\"error\":\"Không có quyền thêm sản phẩm\"}");
//                return;
//            }
//        } catch (Exception ex) {
//            em.close();
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            resp.getWriter().write("{\"error\":\"Lỗi hệ thống (quyền)\"}");
//            ex.printStackTrace();
//            return;
//        }

        // 3. Xử lý tạo mới sản phẩm
        ProductCreateDTO dto = gson.fromJson(req.getReader(), ProductCreateDTO.class);
        try {
            int id = service.create(dto);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"status\":\"success\",\"id\":" + id + "}");
        } catch (IllegalArgumentException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + ex.getMessage() + "\"}");
        } catch (Exception ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            // Chuyển stacktrace thành String
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String stack = sw.toString();

            // Trả về message + stacktrace cho client
            resp.getWriter().write("{"
                    + "\"error\":\"Lỗi hệ thống\","
                    + "\"message\":\"" + ex.getMessage() + "\","
                    + "\"stack\":\"" + stack.replace("\n", "\\n").replace("\r", "").replace("\"", "\\\"") + "\""
                    + "}");
            ex.printStackTrace(); // vẫn in ra log server cho chắc
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin",  "*");
        resp.setContentType("application/json;charset=UTF-8");
        int id = Integer.parseInt(req.getPathInfo().substring(1));
        ProductListRequestDTO rq = gson.fromJson(req.getReader(), ProductListRequestDTO.class);
        ProductListResponseDTO dto = service.updateProduct(id, rq);
        if (dto == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        resp.getWriter().write(gson.toJson(dto));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin",  "*");
        int id = Integer.parseInt(req.getPathInfo().substring(1));
        boolean ok = service.deleteProduct(id);
        resp.setStatus(ok
                ? HttpServletResponse.SC_NO_CONTENT
                : HttpServletResponse.SC_NOT_FOUND);
    }
}
