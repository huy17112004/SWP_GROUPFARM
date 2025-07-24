package dao;

import dto.ProductDashboardDTO;
import entity.Product;
import entity.ProductImage;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDashboardDAO {
    private final EntityManager em;
    public ProductDashboardDAO(EntityManager em) {
        this.em = em;
    }

    public List<ProductDashboardDTO> findAllDashboard() {
        List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();

        return products.stream().map(p -> {
            String imageUrl = p.getImages().stream()
                    .findFirst()
                    .map(ProductImage::getImageUrl)
                    .orElse(null);


            return new ProductDashboardDTO(
                    p.getId(),
                    p.getProductName(),
                    p.getWholesalePrice(),
                    p.getDescription(),
                    imageUrl,
                    p.getCategory().getCategoryName(),
                    p.getCategory().getId()
            );
        }).collect(Collectors.toList());
    }

    public Product findByIdEntity(int id) {
        return em.find(Product.class, id);
    }

    public void deleteEntity(Product p) {
        if (!em.contains(p)) {
            p = em.merge(p);
        }
        em.remove(p);
    }
    public ProductDashboardDTO findDashboardById(int id) {
        Product p = em.find(Product.class, id);
        if (p == null) return null;

        String imageUrl = p.getImages().stream()
                .findFirst()
                .map(ProductImage::getImageUrl)
                .orElse(null);


        return new ProductDashboardDTO(
                p.getId(),
                p.getProductName(),
                p.getWholesalePrice(),
                p.getDescription(),
                imageUrl,
                p.getCategory().getCategoryName(),
                p.getCategory().getId()
        );
    }

    public Product updateEntity(Product p) {
        return em.merge(p);
    }

    public List<ProductDashboardDTO> findRelatedProducts(Long productId, int limit) {
        Product mainProduct = em.find(Product.class, productId);
        if (mainProduct == null) return List.of();

        List<Product> related = em.createQuery(
                        "SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.id <> :productId", Product.class)
                .setParameter("categoryId", mainProduct.getCategory().getId())
                .setParameter("productId", productId)
                .setMaxResults(limit)
                .getResultList();

        return related.stream().map(p -> {
            String imageUrl = p.getImages().stream().findFirst().map(ProductImage::getImageUrl).orElse(null);
            return new ProductDashboardDTO(
                    p.getId(), p.getProductName(), p.getWholesalePrice(), p.getDescription(),
                    imageUrl, p.getCategory().getCategoryName(), p.getCategory().getId()
            );
        }).collect(Collectors.toList());
    }

}
