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
        // Cleanup EntityManagerFactory nếu cần
        // Giải phóng JDBC driver để tránh memory leak trên Tomcat
        try {
            java.util.Enumeration<java.sql.Driver> drivers = java.sql.DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                java.sql.Driver driver = drivers.nextElement();
                if (driver.getClass().getClassLoader() == getClass().getClassLoader()) {
                    java.sql.DriverManager.deregisterDriver(driver);
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
}
