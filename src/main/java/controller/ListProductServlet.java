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

@WebServlet("/api/list-product")
public class ListProductServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> productList = productService.getAllProduct();

        List<ProductResponseDTO> dtoList = productList.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());

        // Chuyển danh sách thành JSON và trả về client
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        String json = gson.toJson(dtoList);
        response.getWriter().write(json);


    }
}
