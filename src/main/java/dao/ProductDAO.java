package dao;

import entity.Product;
import entity.WholesaleCustomer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import util.JpaUtil;

import java.util.List;

public class ProductDAO extends GenericDAO<Product> {
    public ProductDAO(EntityManager em) {
        super(Product.class, em);
    }

    @Override
    public List<Product> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            List<Product> list = em.createQuery(
                    "SELECT DISTINCT p FROM Product p " +
                    "LEFT JOIN FETCH p.images " +
                    "LEFT JOIN FETCH p.category", Product.class).getResultList();
            return list;
        } finally {
            em.close();
        }
    }

    public List<Product> findAllByCategoryId(int categoryId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            List<Product> list = em.createQuery(
                            "SELECT DISTINCT p FROM Product p " +
                                    "LEFT JOIN FETCH p.images " +
                                    "LEFT JOIN FETCH p.category c " +
                                    "WHERE c.id = :categoryId", Product.class)
                    .setParameter("categoryId", categoryId)
                    .getResultList();
            return list;
        } finally {
            em.close();
        }
    }

    public List<Product> searchProducts(List<Integer> categoryIds, Double minPrice, Double maxPrice, String name) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.images LEFT JOIN FETCH p.category WHERE 1=1 ");
            if (categoryIds != null && !categoryIds.isEmpty()) {
                jpql.append("AND p.category.id IN :categoryIds ");
            }
            if (minPrice != null) {
                jpql.append("AND p.wholesalePrice >= :minPrice ");
            }
            if (maxPrice != null) {
                jpql.append("AND p.wholesalePrice <= :maxPrice ");
            }
            if (name != null && !name.isEmpty()) {
                jpql.append("AND LOWER(p.productName) LIKE :name ");
            }

            TypedQuery<Product> query = em.createQuery(jpql.toString(), Product.class);

            if (categoryIds != null && !categoryIds.isEmpty()) {
                query.setParameter("categoryIds", categoryIds);
            }
            if (minPrice != null) {
                query.setParameter("minPrice", minPrice);
            }
            if (maxPrice != null) {
                query.setParameter("maxPrice", maxPrice);
            }
            if (name != null && !name.isEmpty()) {
                query.setParameter("name", "%" + name.toLowerCase() + "%");
            }

            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
