package service;

import dao.*;
import dto.CustomerAddressDetailDTO;
import dto.CustomerAddressListDTO;
import entity.Address;
import entity.CustomerAddress;
import entity.Ward;
import entity.WholesaleCustomer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import util.JpaUtil;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerAddressService {

    public CustomerAddressDetailDTO getCustomerAddressDetail(int addressId) {
        EntityManager em = JpaUtil.getEntityManager();
        CustomerAddressDAO dao = new CustomerAddressDAO(em);
        CustomerAddress ca = dao.findById(addressId);
        if (ca == null) {
            return null;
        }
        CustomerAddressDetailDTO dto = new CustomerAddressDetailDTO();
        dto.setId(ca.getId());
        dto.setStreet(ca.getAddress().getStreet());
        dto.setWardID(ca.getAddress().getWard().getId());
        dto.setDistrictID(ca.getAddress().getWard().getDistrict().getId());
        dto.setProvinceID(ca.getAddress().getWard().getDistrict().getProvince().getId());
        dto.setLatitude(ca.getAddress().getLatitude());
        dto.setLongitude(ca.getAddress().getLongitude());
        return dto;
    }

    public List<CustomerAddressListDTO> getCustomerAddresses(int accountId) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();

        CustomerAddressDAO customerAddressDAO = new CustomerAddressDAO(em);

        // Lấy list CustomerAddress của customer này
        List<CustomerAddress> addresses = customerAddressDAO.findByCustomer(accountId);

        List<CustomerAddressListDTO> dtos = addresses.stream().map(ca -> {
            CustomerAddressListDTO dto = new CustomerAddressListDTO();
            dto.setId(ca.getId());
            dto.setStreet(ca.getAddress().getStreet());
            dto.setWardName(ca.getAddress().getWard().getName());
            dto.setDistrictName(ca.getAddress().getWard().getDistrict().getName());
            dto.setProvinceName(ca.getAddress().getWard().getDistrict().getProvince().getName());
            return dto;
        }).collect(Collectors.toList());
        return dtos;
    }

    public boolean addCustomerAddress(Integer accountID, String street, Integer wardID, Float latitude, Float longitude) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        WholesaleCustomerDAO customerDAO = new WholesaleCustomerDAO(em);
        WardDAO wardDAO = new WardDAO(em);
        CustomerAddressDAO customerAddressDAO = new CustomerAddressDAO(em);
        AddressDAO addressDAO = new AddressDAO(em);

        try {
            tx.begin();

            // 1. Lấy customer từ accountID
            WholesaleCustomer customer = customerDAO.findById(accountID);
            if (customer == null) {
                System.out.println("Customer null! accountID = " + accountID);
                return false;
            }

            // 2. Lấy ward
            Ward ward = wardDAO.findById(wardID);
            if (ward == null) {
                System.out.println("Ward null! wardID = " + wardID);
                return false;
            }

            // 3. Tạo Address mới
            Address address = new Address();
            address.setStreet(street);
            address.setWard(ward);
            address.setLatitude(latitude);
            address.setLongitude(longitude);

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

    public boolean updateCustomerAddress(int accountId, int addressId, String street, Integer wardID, Float latitude, Float longitude) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        CustomerAddressDAO customerAddressDAO = new CustomerAddressDAO(em);
        WardDAO wardDAO = new WardDAO(em);

        try {
            tx.begin();

            // 1. Tìm CustomerAddress theo id
            CustomerAddress customerAddress = customerAddressDAO.findById(addressId);
            if (customerAddress == null) {
                System.out.println("CustomerAddress not found! addressId = " + addressId);
                return false;
            }

            // 2. Check chủ sở hữu
            if (customerAddress.getWholesaleCustomer() == null || customerAddress.getWholesaleCustomer().getId() != accountId) {
                System.out.println("Not owner!");
                return false;
            }

            // 3. Lấy Address
            Address address = customerAddress.getAddress();

            // 4. Lấy Ward mới
            Ward ward = wardDAO.findById(wardID);
            if (ward == null) {
                System.out.println("Ward not found! wardID = " + wardID);
                return false;
            }

            // 5. Update các trường
            address.setStreet(street);
            address.setWard(ward);
            address.setLatitude(latitude);
            address.setLongitude(longitude);

            // Hibernate sẽ tự merge do liên kết, hoặc bạn có thể gọi em.merge(address);

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

    public boolean deleteCustomerAddress(int accountId, int addressId) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        CustomerAddressDAO customerAddressDAO = new CustomerAddressDAO(em);

        try {
            tx.begin();

            // 1. Tìm CustomerAddress theo id
            CustomerAddress customerAddress = customerAddressDAO.findById(addressId);
            if (customerAddress == null) {
                System.out.println("CustomerAddress not found! addressId = " + addressId);
                return false;
            }

            // 2. Check chủ sở hữu
            if (customerAddress.getWholesaleCustomer() == null || customerAddress.getWholesaleCustomer().getId() != accountId) {
                System.out.println("Not owner!");
                return false;
            }

            // 3. Xóa CustomerAddress
            em.remove(customerAddress); // Nếu cascade ALL, Hibernate sẽ xóa luôn Address

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
