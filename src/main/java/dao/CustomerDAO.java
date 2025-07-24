package dao;

import entity.WholesaleCustomer;
import jakarta.persistence.EntityManager;
import java.util.Optional;

public class CustomerDAO {
    private final EntityManager em;

    public CustomerDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Tìm WholesaleCustomer theo email (username)
     */
    public WholesaleCustomer findByEmail(String email) {
        Optional<WholesaleCustomer> opt = em.createQuery(
                        "SELECT c FROM WholesaleCustomer c WHERE c.email = :email", WholesaleCustomer.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
        return opt.orElse(null);
    }

    /**
     * Lưu mới WholesaleCustomer (bao gồm cả Account do @Inheritance)
     */
    public void save(WholesaleCustomer customer) {
        em.persist(customer);
    }

    /**
     * Cập nhật WholesaleCustomer
     */
    public void update(WholesaleCustomer customer) {
        em.merge(customer);
    }

    /**
     * Tìm theo ID
     */
    public WholesaleCustomer findById(int id) {
        return em.find(WholesaleCustomer.class, id);
    }
}