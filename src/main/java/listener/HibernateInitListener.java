package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import util.JpaUtil;

import jakarta.persistence.EntityManager;

@WebListener
public class HibernateInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();
        System.out.println("✅ Hibernate auto-initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Optional: cleanup code
    }
}
