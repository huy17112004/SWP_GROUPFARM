// dao/CategoryDAO.java
package dao;

import entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;


public class CategoryDAO {
    private final EntityManager em;

    public CategoryDAO(EntityManager em) {
        this.em = em;
    }

    public Category findById(int id) {
        return em.find(Category.class, id);
    }

    public List<Category> findAll() {
        TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c ORDER BY c.id", Category.class);
        return query.getResultList();
    }

    public Category findByName(String name) {
        TypedQuery<Category> query = em.createQuery(
                "SELECT c FROM Category c WHERE LOWER(c.categoryName) = :name", Category.class
        );
        query.setParameter("name", name.toLowerCase());
        return query.getResultStream().findFirst().orElse(null);
    }
    public void create(Category category) {
        em.persist(category);
    }

    public Category update(Category category) {
        return em.merge(category);
    }


    public void delete(Category category) {
        Category managed = category;
        if (!em.contains(category)) {
            managed = em.merge(category);
        }
        em.remove(managed);
    }
}
