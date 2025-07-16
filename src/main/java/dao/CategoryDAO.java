// dao/CategoryDAO.java
package dao;

import entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * DAO for Category entity, providing CRUD operations.
 */
public class CategoryDAO {
    private final EntityManager em;

    public CategoryDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Find a category by its ID.
     * @param id the category ID
     * @return the Category or null if not found
     */
    public Category findById(int id) {
        return em.find(Category.class, id);
    }

    /**
     * Retrieve all categories.
     * @return list of categories
     */
    public List<Category> findAll() {
        TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
        return query.getResultList();
    }

    /**
     * Find a category by its name (case-insensitive).
     * @param name the category name
     * @return the Category or null if not found
     */
    public Category findByName(String name) {
        TypedQuery<Category> query = em.createQuery(
                "SELECT c FROM Category c WHERE LOWER(c.categoryName) = :name", Category.class
        );
        query.setParameter("name", name.toLowerCase());
        return query.getResultStream().findFirst().orElse(null);
    }

    /**
     * Persist a new category.
     * @param category the category to create
     */
    public void create(Category category) {
        em.persist(category);
    }

    /**
     * Merge (update) an existing category.
     * @param category the category to update
     * @return the managed Category instance
     */
    public Category update(Category category) {
        return em.merge(category);
    }

    /**
     * Remove a category.
     * @param category the category to delete (must be managed or merged)
     */
    public void delete(Category category) {
        Category managed = category;
        if (!em.contains(category)) {
            managed = em.merge(category);
        }
        em.remove(managed);
    }
}
