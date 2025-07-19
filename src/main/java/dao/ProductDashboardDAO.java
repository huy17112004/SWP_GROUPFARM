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
            List<String> imageUrls = p.getImages().stream()
                    .map(ProductImage::getImageUrl)
                    .collect(Collectors.toList());

            return new ProductDashboardDTO(
                    p.getId(),
                    p.getProductName(),
                    p.getWholesalePrice(),
                    p.getDescription(),
                    imageUrls,
                    p.getCategory().getCategoryName()
            );
        }).collect(Collectors.toList());
    }

    public Product findByIdEntity(Long id) {
        return em.find(Product.class, id);
    }

    public void deleteEntity(Product p) {
        if (!em.contains(p)) {
            p = em.merge(p);
        }
        em.remove(p);
    }
//    public ProductDashboardDTO findDashboardById(int id) {
//        Product p = em.find(Product.class, id);
//        if (p == null) return null;
//
//        List<String> imageUrls = p.getImages().stream()
//                .map(ProductImage::getImageUrl)
//                .collect(Collectors.toList());
//
//        return new ProductDashboardDTO(
//                p.getId(),
//                p.getProductName(),
//                p.getWholesalePrice(),
//                p.getDescription(),
//                imageUrls,
//                p.getCategory().getCategoryName()
//        );
//    }

}
