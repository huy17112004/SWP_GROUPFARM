package controller;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ProductService;

import java.io.IOException;

@WebServlet("/api/products/related")
public class RelatedProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        productService = new ProductService(); // inject DAO bên trong
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int productId = Integer.parseInt(req.getParameter("productId"));
        List<ProductDTO> relatedProducts = productService.getRelatedProducts(productId);

        resp.setContentType("application/json");
        new ObjectMapper().writeValue(resp.getWriter(), relatedProducts);
    }
}
