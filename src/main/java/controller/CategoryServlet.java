package controller;

import com.google.gson.Gson;
import dto.CategoryRequestDTO;
import dto.CategoryResponseDTO;
import entity.Category;
import service.CategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/api/categories/*")
public class CategoryServlet extends HttpServlet {
    private final CategoryService service = new CategoryService();
    private final Gson gson = new Gson();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET ,POST, DELETE, PUT, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo(); // null hoặc "/{id}"

        if (path != null && path.length() > 1) {
            int id = Integer.parseInt(path.substring(1));
            Category c = service.getCategoryById(id);
            if (c == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            CategoryResponseDTO dto = new CategoryResponseDTO(c.getId(), c.getCategoryName());
            resp.getWriter().write(gson.toJson(dto));
        } else {
            List<Category> list = service.getAllCategories();
            List<CategoryResponseDTO> dtos = list.stream()
                    .map(c -> new CategoryResponseDTO(c.getId(), c.getCategoryName()))
                    .collect(Collectors.toList());
            resp.getWriter().write(gson.toJson(dtos));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        CategoryRequestDTO reqDto = gson.fromJson(req.getReader(), CategoryRequestDTO.class);
        Category created = service.createCategory(reqDto.getCategoryName());
        CategoryResponseDTO dto = new CategoryResponseDTO(created.getId(), created.getCategoryName());
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(gson.toJson(dto));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        String path = req.getPathInfo();
        int id = Integer.parseInt(path.substring(1));
        CategoryRequestDTO reqDto = gson.fromJson(req.getReader(), CategoryRequestDTO.class);
        Category updated = service.updateCategory(id, reqDto.getCategoryName());
        if (updated == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        CategoryResponseDTO dto = new CategoryResponseDTO(updated.getId(), updated.getCategoryName());
        resp.getWriter().write(gson.toJson(dto));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        String path = req.getPathInfo();
        int id = Integer.parseInt(path.substring(1));
        boolean ok = service.deleteCategory(id);
        resp.setStatus(ok ? HttpServletResponse.SC_NO_CONTENT : HttpServletResponse.SC_NOT_FOUND);
    }
}
