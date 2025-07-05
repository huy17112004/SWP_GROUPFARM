package service;

import dao.ProductDAO;
import dto.ProductResponseDTO;
import entity.Product;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

    // Nếu DAO chưa có searchProductsDTO mà chỉ có searchProducts trả về entity:
     public List<ProductResponseDTO> searchProducts(Integer categoryId, String name, String sortType, int page, int size) {
         EntityManager em = JpaUtil.getEntityManager();
         try {
             ProductDAO productDAO = new ProductDAO(em);
             return productDAO.searchProducts(categoryId, name, sortType, page, size)
                              .stream()
                              .map(ProductResponseDTO::new)
                              .collect(Collectors.toList());
         } finally {
             em.close();
         }
     }

    // Đếm tổng số sản phẩm (không cần sửa)
    public long countProducts(Integer categoryId, String name) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            ProductDAO productDAO = new ProductDAO(em);
            return productDAO.countProducts(categoryId, name);
        } finally {
            em.close();
        }
    }

    // Nếu muốn findAll trả về DTO luôn:
    public List<ProductResponseDTO> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            ProductDAO productDAO = new ProductDAO(em);
            return productDAO.findAll()
                    .stream()
                    .map(ProductResponseDTO::new)
                    .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }
}
