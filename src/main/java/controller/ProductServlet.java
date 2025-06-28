package controller;

import com.google.gson.Gson;
import dto.ProductRequestDTO;
import dto.ProductResponseDTO;
import entity.Product;
import entity.Category;
import service.ProductService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/api/products/*")
public class ProductServlet extends HttpServlet {
    private final ProductService service = new ProductService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String path = req.getPathInfo();
        if (path != null && path.length()>1) {
            int id = Integer.parseInt(path.substring(1));
            Product p = service.getProductById(id);
            if (p == null) { resp.setStatus(HttpServletResponse.SC_NOT_FOUND); return; }
            resp.getWriter().write(gson.toJson(toDTO(p)));
        } else {
            String search = req.getParameter("search");
            String cat = req.getParameter("categoryId");
            Integer categoryId = cat!=null?Integer.parseInt(cat):null;
            List<Product> list = service.getAllProducts(search, categoryId);
            List<ProductResponseDTO> dtos = list.stream().map(this::toDTO).collect(Collectors.toList());
            resp.getWriter().write(gson.toJson(dtos));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProductRequestDTO rq = gson.fromJson(req.getReader(), ProductRequestDTO.class);
        Product p = new Product();
        p.setProductName(rq.getProductName());
        p.setEntryPrice(rq.getEntryPrice());
        p.setRetailPrice(rq.getRetailPrice());
        p.setWholesalePrice(rq.getWholesalePrice());
        p.setDescription(rq.getDescription());
        Product created = service.createProduct(p, rq.getCategoryId());
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(gson.toJson(toDTO(created)));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        int id = Integer.parseInt(path.substring(1));
        ProductRequestDTO rq = gson.fromJson(req.getReader(), ProductRequestDTO.class);
        Product newData = new Product();
        newData.setProductName(rq.getProductName());
        newData.setEntryPrice(rq.getEntryPrice());
        newData.setRetailPrice(rq.getRetailPrice());
        newData.setWholesalePrice(rq.getWholesalePrice());
        newData.setDescription(rq.getDescription());
        Product updated = service.updateProduct(id, newData, rq.getCategoryId());
        if (updated==null) { resp.setStatus(HttpServletResponse.SC_NOT_FOUND); return; }
        resp.getWriter().write(gson.toJson(toDTO(updated)));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        int id = Integer.parseInt(path.substring(1));
        boolean ok = service.deleteProduct(id);
        resp.setStatus(ok?HttpServletResponse.SC_NO_CONTENT:HttpServletResponse.SC_NOT_FOUND);
    }

    private ProductResponseDTO toDTO(Product p) {
        Category c = p.getCategory();
        return new ProductResponseDTO(
                p.getId(), p.getProductName(), p.getEntryPrice(),
                p.getRetailPrice(), p.getWholesalePrice(), p.getDescription(),
                c!=null?c.getId():0, c!=null?c.getCategoryName():null
        );
    }
}
