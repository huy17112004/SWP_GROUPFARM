package service;

import dao.ProductDAO;
import dto.ProductCreateDTO;
import dto.ProductResponseDTO;
import entity.Category;
import entity.Product;
import entity.ProductImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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

    public Product findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            ProductDAO productDAO = new ProductDAO(em);
            return productDAO.findById(id);
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
