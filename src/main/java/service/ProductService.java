package service;

import dao.ProductDAO;
import dto.ProductCreateDTO;
import dto.ProductResponseDTO;
import dto.ProductViewDTO;
import entity.Category;
import entity.Product;
import entity.ProductImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import util.JpaUtil;

import java.util.ArrayList;
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

    public List<ProductViewDTO> searchProductHaveEntryPrice(
            Integer categoryId, String name, String sortType, int page, int size) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            ProductDAO dao = new ProductDAO(em);
            return dao.searchProducts(categoryId, name, sortType, page, size)
                    .stream()
                    .map(ProductViewDTO::new)
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
                if (p.getImages() == null) {
                    p.setImages(new ArrayList<>());
                }
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
    public Product updateProduct(int id, ProductCreateDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Product p = em.find(Product.class, id);
            if (p == null) throw new IllegalArgumentException("Product không tồn tại: " + id);
            // cập nhật các trường
            p.setProductName(dto.getProductName());
            p.setEntryPrice(dto.getEntryPrice());
            p.setRetailPrice(dto.getRetailPrice());
            p.setWholesalePrice(dto.getWholesalePrice());
            p.setDescription(dto.getDescription());
            // category
            Category cat = em.find(Category.class, dto.getCategoryId());
            if (cat == null) throw new IllegalArgumentException("Category không tồn tại: " + dto.getCategoryId());
            p.setCategory(cat);
            // images: có thể xóa-cascade rồi thêm mới hoặc cập nhật từng phần
            p.getImages().clear();
            for (String url : dto.getImageUrls()) {
                if (url != null && !url.isBlank()) {
                    ProductImage img = new ProductImage();
                    img.setImageUrl(url);
                    img.setProduct(p);
                    p.getImages().add(img);
                }
            }
            em.merge(p);
            tx.commit();
            return p;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void deleteProduct(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Product p = em.find(Product.class, id);
            if (p == null) throw new IllegalArgumentException("Product không tồn tại: " + id);
            em.remove(p);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

}
