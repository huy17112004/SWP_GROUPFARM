package dao;

import jakarta.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

    public class GenericDAO<T> {
    protected EntityManager em;
    private final Class<T> type;

    public GenericDAO(Class<T> type, EntityManager em) {
        this.type = type;
        this.em = em;
    }

    public void save(T entity) {
        em.persist(entity);
    }

    public void update(T entity) {
        em.merge(entity);
    }

    public void delete(T entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    public T findById(Serializable id) {
        return em.find(type, id);
    }

    public List<T> findAll() {
        return em.createQuery("FROM " + type.getSimpleName(), type).getResultList();
    }

    public void deleteAll() {
        em.createQuery("DELETE FROM " + type.getSimpleName()).executeUpdate();
    }
}