package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import util.JpaUtil;

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
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void update(T entity) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void delete(T entity) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity)); // ensure managed
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public T findById(Serializable id) {
        return em.find(type, id);
    }

    public List<T> findAll() {
        return em.createQuery("FROM " + type.getSimpleName(), type).getResultList();
    }

    public void deleteAll() {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("DELETE FROM " + type.getSimpleName()).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
