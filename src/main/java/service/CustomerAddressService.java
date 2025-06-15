package service;

import dao.AddressDAO;
import dao.CustomerAddressDAO;
import dao.WardDAO;
import dao.WholesaleCustomerDAO;
import entity.Address;
import entity.CustomerAddress;
import entity.Ward;
import entity.WholesaleCustomer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import util.JpaUtil;

public class CustomerAddressService {
    public static boolean addCustomerAddress(Integer accountId, String street, Integer wardId) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        WholesaleCustomerDAO customerDAO = new WholesaleCustomerDAO(em);
        WardDAO wardDAO = new WardDAO(em);
        CustomerAddressDAO customerAddressDAO = new CustomerAddressDAO(em);
        AddressDAO addressDAO = new AddressDAO(em);

        try {
            tx.begin();

            // 1. Lấy customer từ accountID
            WholesaleCustomer customer = customerDAO.findById(accountId);
            if (customer == null) return false;

            // 2. Lấy ward
            Ward ward = wardDAO.findById(wardId);
            if (ward == null) return false;

            // 3. Tạo Address mới
            Address address = new Address();
            address.setStreet(street);
            address.setWard(ward);
            addressDAO.save(address);


            // 4. Tạo CustomerAddress mới, liên kết Address & Customer
            CustomerAddress ca = new CustomerAddress();
            ca.setWholesaleCustomer(customer);
            ca.setAddress(address);
            customerAddressDAO.save(ca);

            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}
