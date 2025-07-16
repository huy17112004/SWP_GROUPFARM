package service;

import dao.ProductCreateDAO;
import dao.ProductListDAO;
import dto.ProductCreateDTO;
import dto.ProductListRequestDTO;
import dto.ProductListResponseDTO;
import entity.Category;
import entity.Product;
import entity.ProductImage;
import util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class ProductListService {

    // Lấy list (có search / category)
    public List<ProductListResponseDTO> getAllProducts(String search, Integer categoryId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            ProductListDAO dao = new ProductListDAO(em);
            return dao.findAllProductLots();
        } finally {
            em.close();
        }
    }

    // Lấy detail DTO theo id
    public ProductListResponseDTO getProductDTOById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return new ProductListDAO(em).findProductListById(id);
        } finally {
            em.close();
        }
    }

    // Tạo mới, trả về DTO đầy đủ
    public int create(ProductCreateDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Category category = em.find(Category.class, dto.getCategoryId());
            if (category == null)
                throw new IllegalArgumentException("Category không tồn tại!");

            Product product = new Product();
            product.setProductName(dto.getProductName());
            product.setEntryPrice(dto.getEntryPrice());
            product.setRetailPrice(dto.getRetailPrice());
            product.setWholesalePrice(dto.getWholesalePrice());
            product.setDescription(dto.getDescription());
            product.setCategory(category);

            new ProductCreateDAO(em).create(product);
            tx.commit();
            return product.getId();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }



        public ProductListResponseDTO updateProduct(int id, ProductListRequestDTO rq) {
            EntityManager em = JpaUtil.getEntityManager();
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Product p = new ProductListDAO(em).findByIdEntity(id);
                if (p == null) {
                    tx.rollback();
                    return null;
                }

                p.setProductName(rq.getProductName());
                p.setEntryPrice(rq.getEntryPrice().intValue());
                p.setRetailPrice(rq.getRetailPrice().intValue());
                p.setWholesalePrice(rq.getWholesalePrice());
                p.setDescription(rq.getDescription());
                Category c = em.find(Category.class, rq.getCategoryId());
                p.setCategory(c);
                // xử lý ảnh nếu cần…

                tx.commit();
                return new ProductListDAO(em).findProductListById(p.getId());
            } catch (RuntimeException ex) {
                if (tx.isActive()) tx.rollback();
                throw ex;
            } finally {
                em.close();
            }
        }


        // Xoá
        public boolean deleteProduct(int id) {
            EntityManager em = JpaUtil.getEntityManager();
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                ProductListDAO dao = new ProductListDAO(em);
                Product p = dao.findByIdEntity(id);
                if (p == null) {
                    tx.rollback();
                    return false;
                }
                dao.deleteEntity(p);
                tx.commit();
                return true;
            } catch (RuntimeException ex) {
                if (tx.isActive()) tx.rollback();
                throw ex;
            } finally {
                em.close();
            }
        }
    }
