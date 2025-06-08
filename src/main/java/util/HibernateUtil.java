package util;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.addAnnotatedClass(entity.Account.class);
            configuration.addAnnotatedClass(entity.Admin.class);
            configuration.addAnnotatedClass(entity.Saler.class);
            configuration.addAnnotatedClass(entity.Manager.class);
            configuration.addAnnotatedClass(entity.Shipper.class);
            configuration.addAnnotatedClass(entity.WholesaleCustomer.class);
            configuration.addAnnotatedClass(entity.WarehouseManager.class);
            configuration.addAnnotatedClass(entity.Province.class);
            configuration.addAnnotatedClass(entity.Ward.class);
            configuration.addAnnotatedClass(entity.District.class);
            configuration.addAnnotatedClass(entity.Address.class);
            configuration.addAnnotatedClass(entity.CustomerAddress.class);
            configuration.addAnnotatedClass(entity.Warehouse.class);
            configuration.addAnnotatedClass(entity.Cart.class);
            configuration.addAnnotatedClass(entity.Category.class);
            configuration.addAnnotatedClass(entity.Contract.class);
            configuration.addAnnotatedClass(entity.Customer.class);
            configuration.addAnnotatedClass(entity.OrderRisk.class);
            configuration.addAnnotatedClass(entity.Product.class);
            configuration.addAnnotatedClass(entity.ProductImage.class);
            configuration.addAnnotatedClass(entity.ShippingLog.class);
            configuration.addAnnotatedClass(entity.WholesaleOrder.class);
            configuration.addAnnotatedClass(entity.WholesaleOrderItem.class);


            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception ex) {
            System.err.println("SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
