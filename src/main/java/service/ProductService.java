package service;

import dao.ProductDAO;
import dto.ProductResponseDTO;
import entity.Product;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

import java.util.List;
import java.util.stream.Collectors;

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

    public Product findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            ProductDAO productDAO = new ProductDAO(em);
            return productDAO.findById(id);
        } finally {
            em.close();
        }
    }


//    public int countAllProducts() {
//        EntityManager em = JpaUtil.getEntityManager();
//        try {
//            ProductDAO dao = new ProductDAO(em);
//            return dao.totalProducts();
//        } finally {
//            em.close();
//        }
//    }

}
