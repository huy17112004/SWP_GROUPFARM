import entity.Account;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class main {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // Tạo mới một Account
            Account acc = new Account();
            acc.setUsername("testuser");
            acc.setPassword("123456");
            session.persist(acc);


            tx.commit();
            System.out.println("✅ Dữ liệu đã được lưu thành công!");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            HibernateUtil.getSessionFactory().close();
        }
    }
}
