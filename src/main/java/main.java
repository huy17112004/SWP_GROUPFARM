import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import util.JpaUtil;

public class main {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();
            tx.begin();

            // TODO: em.persist(entity) nếu muốn lưu đối tượng

            tx.commit();
            System.out.println("✅ Transaction committed successfully!");
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
