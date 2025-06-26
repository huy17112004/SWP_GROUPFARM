package dao;

import entity.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

public class MessageDAO extends GenericDAO<Message> {
    public MessageDAO(EntityManager em) {
        super(Message.class, em);
    }

    public List<Message> findByOrderId(int orderId) {
        TypedQuery<Message> query = em.createQuery(
                "SELECT m FROM Message m WHERE m.order.id = :orderId ORDER BY m.createdAt ASC",
                Message.class
        );
        query.setParameter("orderId", orderId);
        return query.getResultList();

    }

    public List<Message> findByOrderIdAndCreatedAfter(int orderId, LocalDateTime since) {
        return em.createQuery(
                        "SELECT m FROM Message m WHERE m.order.id = :orderId AND m.createdAt > :since ORDER BY m.createdAt ASC",
                        Message.class)
                .setParameter("orderId", orderId)
                .setParameter("since", since)
                .getResultList();
    }
}
