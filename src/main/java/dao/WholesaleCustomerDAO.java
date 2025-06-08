package dao;

import entity.WholesaleCustomer;
import org.hibernate.Session;
import util.HibernateUtil;

import java.io.Serializable;

public class WholesaleCustomerDAO extends GenericDAO<WholesaleCustomer> {
    public WholesaleCustomerDAO() {
        super(WholesaleCustomer.class);
    }

    public WholesaleCustomer findByCompanyName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM WholesaleCustomer WHERE companyName = :companyName";
            return session.createQuery(hql, WholesaleCustomer.class)
                    .setParameter("companyName", name)
                    .uniqueResult();
        }
    }
}
