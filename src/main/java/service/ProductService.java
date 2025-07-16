package service;

import dao.ProductCreateDAO;
import dao.ProductDAO;
import dto.ProductCreateDTO;
import entity.Category;
import entity.Product;
import entity.ProductImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import util.JpaUtil;

import java.util.ArrayList;
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

    public Product createProduct(ProductCreateDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Category cat = em.find(Category.class, dto.getCategoryId());
            if (cat == null) {
                throw new IllegalArgumentException("Category không tồn tại: " + dto.getCategoryId());
            }

            Product p = new Product();
            p.setProductName(dto.getProductName());
            p.setEntryPrice(dto.getEntryPrice());
            p.setRetailPrice(dto.getRetailPrice());
            p.setWholesalePrice(dto.getWholesalePrice());
            p.setDescription(dto.getDescription());
            p.setCategory(cat);

            // Thêm ảnh vào list, JPA sẽ cascade persist
            if (dto.getImageUrls() != null) {
                for (String url : dto.getImageUrls()) {
                    if (url != null && !url.isBlank()) {
                        ProductImage img = new ProductImage();
                        img.setImageUrl(url);
                        img.setProduct(p);
                        p.getImages().add(img);
                    }
                }
            }

            em.persist(p);   // cả product và images đều được lưu
            tx.commit();
            return p;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}