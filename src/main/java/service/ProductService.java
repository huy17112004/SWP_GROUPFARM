package service;

import dao.ProductDAO;
import entity.Product;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

import java.util.List;

public class ProductService {

    public List<Product> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            ProductDAO productDAO = new ProductDAO(em);
            return productDAO.findAll();
        } finally {
            em.close();
        }
    }

    public List<Product> searchProducts(List<Integer> categoryIds, Double minPrice, Double maxPrice, String name) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            ProductDAO productDAO = new ProductDAO(em);
            return productDAO.searchProducts(categoryIds, minPrice, maxPrice, name);
        } finally {
            em.close();
        }
    }

}
