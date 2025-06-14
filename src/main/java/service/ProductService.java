package service;

import dao.ProductDAO;
import entity.Product;

import java.util.List;

public class ProductService {
    private final ProductDAO productDAO = new ProductDAO();

    public List<Product> findAll() {
        return productDAO.findAll();
    }

    public List<Product> searchProducts(List<Integer> categoryIds, Double minPrice, Double maxPrice, String name) {
        return productDAO.searchProducts(categoryIds, minPrice, maxPrice, name);
    }

}
