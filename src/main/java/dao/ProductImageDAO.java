package dao;

import entity.ProductImage;
import jakarta.persistence.EntityManager;

public class ProductImageDAO extends GenericDAO<ProductImage>{
    public ProductImageDAO(EntityManager em) {
        super(ProductImage.class, em);
    }
}
