package dao;

import entity.Product;
import entity.WholesaleCustomer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import util.JpaUtil;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProductDAO extends GenericDAO<Product> {
    public ProductDAO(EntityManager em) {
        super(Product.class, em);
    }

    @Override
    public List<Product> findAll() {
        List<Product> list = em.createQuery(
                "SELECT DISTINCT p FROM Product p " +
                        "LEFT JOIN FETCH p.images " +
                        "LEFT JOIN FETCH p.category", Product.class).getResultList();
        return list;
    }

    public List<Product> findAllByCategoryId(int categoryId) {
        List<Product> list = em.createQuery(
                        "SELECT DISTINCT p FROM Product p " +
                                "LEFT JOIN FETCH p.images " +
                                "LEFT JOIN FETCH p.category c " +
                                "WHERE c.id = :categoryId", Product.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
        return list;
    }

    public List<Product> searchProducts(Integer categoryId, String name, String sortType, int page, int size) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM Product p LEFT JOIN p.category c WHERE 1=1 ");
        if (categoryId != null) {
            jpql.append("AND c.id = :categoryId ");
        }
        if (name != null && !name.isEmpty()) {
            jpql.append("AND LOWER(p.productName) LIKE :name ");
        }
        // Sorting...
        if (sortType != null) {
            switch (sortType) {
                case "price_asc": jpql.append("ORDER BY p.wholesalePrice ASC "); break;
                case "price_desc": jpql.append("ORDER BY p.wholesalePrice DESC "); break;
                case "name_asc": jpql.append("ORDER BY p.productName ASC "); break;
                case "name_desc": jpql.append("ORDER BY p.productName DESC "); break;
            }
        }

        TypedQuery<Product> query = em.createQuery(jpql.toString(), Product.class);

        if (categoryId != null) query.setParameter("categoryId", categoryId);
        if (name != null && !name.isEmpty()) query.setParameter("name", "%" + name.toLowerCase() + "%");

        query.setFirstResult(page * size);
        query.setMaxResults(size);

        return query.getResultList();
    }


    public long countProducts(Integer categoryId, String name) {
        StringBuilder jpql = new StringBuilder("SELECT COUNT(p) FROM Product p WHERE 1=1 ");
        if (categoryId != null) {
            jpql.append("AND p.category.id = :categoryId ");
        }
        if (name != null && !name.isEmpty()) {
            jpql.append("AND LOWER(p.productName) LIKE :name ");
        }
        TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);
        if (categoryId != null) {
            query.setParameter("categoryId", categoryId);
        }
        if (name != null && !name.isEmpty()) {
            query.setParameter("name", "%" + name.toLowerCase() + "%");
        }
        return query.getSingleResult();
    }

    // find product detail
    public Product findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT p FROM Product p " +
                    "LEFT JOIN FETCH p.images " +
                    "LEFT JOIN FETCH p.category " +
                    "WHERE p.id = :id";

            return em.createQuery(jpql, Product.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public int totalProducts() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(p.id) FROM Product p";
            Long result = em.createQuery(jpql, Long.class).getSingleResult();
            return result != null ? result.intValue() : 0;
        } finally {
            em.close();
        }
    }
}