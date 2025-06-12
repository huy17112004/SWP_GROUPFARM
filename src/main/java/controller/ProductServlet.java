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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productService.findAll();
        List<ProductResponseDTO> dtoList = products.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String json = gson.toJson(dtoList);
        response.getWriter().write(json);
    }
}
