package dao;

import dto.ProductListResponseDTO;
import entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProductListDAO {
    private final EntityManager em;
    public ProductListDAO(EntityManager em) { this.em = em; }

    // 1) List tất cả
    public List<ProductListResponseDTO> findAllProductLots() {
        String jpql =
                "SELECT new dto.ProductListResponseDTO(" +
                        "   (SELECT pi.imageUrl FROM ProductImage pi " +
                        "     WHERE pi.product.id = s.product.id " +
                        "       AND pi.id = (SELECT MIN(pi2.id) FROM ProductImage pi2 WHERE pi2.product.id = s.product.id)" +
                        "   ), " +
                        "   p.productName, p.retailPrice, c.categoryName, w.warehouseName, " +
                        "   s.id, s.quantity, s.expiredDate, s.importDate" +
                        ") " +
                        "FROM StockLot s " +
                        "LEFT JOIN s.product p " +
                        "LEFT JOIN p.category c " +
                        "LEFT JOIN s.warehouse w " +
                        "ORDER BY s.importDate DESC";

        return em.createQuery(jpql, ProductListResponseDTO.class)
                .getResultList();
    }

    // 2) Detail theo id
    public ProductListResponseDTO
    findProductListById(int id) {
        String jpql =
                "SELECT new dto.ProductListResponseDTO(" +
                        "  (SELECT pi.imageUrl FROM ProductImage pi " +
                        "     WHERE pi.product.id = p.id " +
                        "       AND pi.id = (SELECT MIN(pi2.id) FROM ProductImage pi2 WHERE pi2.product.id = p.id)" +
                        "  ), " +
                        "  p.productName, p.retailPrice, c.categoryName, s.quantity, s.expiredDate, s.importDate" +
                        ") " +
                        "FROM Product p " +
                        "LEFT JOIN p.category c " +
                        "LEFT JOIN p.stockLots s " +
                        "WHERE p.id = :id";

        return em.createQuery(jpql, ProductListResponseDTO.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    // 5) CRUD cho entity Product (Service sẽ dùng)
    public Product findByIdEntity(int id) {
        return em.find(Product.class, id);
    }

    public void createEntity(Product p) {
        em.persist(p);
    }

    public Product updateEntity(Product p) {
        return em.merge(p);
    }

    public void deleteEntity(Product p) {
        if (!em.contains(p)) p = em.merge(p);
        em.remove(p);
    }
}
