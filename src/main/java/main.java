import entity.Account;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class main {
    public static void main(String[] args) {
        // Mở session từ HibernateUtil
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            tx.commit();
            System.out.println("✅ Account saved successfully!");
            String a = "ádasd";
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            HibernateUtil.getSessionFactory().close();
        }
    }
}
