package service;

import dao.DistrictDAO;
import dao.ProvinceDAO;
import dao.WardDAO;
import dto.DistrictExternalDTO;
import dto.LocationDTO;
import dto.ProvinceExternalDTO;
import dto.WardExternalDTO;
import entity.District;
import entity.Province;
import entity.Ward;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import util.JpaUtil;

import java.util.List;
import java.util.stream.Collectors;

public class LocationService {
    public void importFullLocation(List<ProvinceExternalDTO> provinceExternalList) {
        // 1. Tạo EntityManager & Transaction
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        // 2. Truyền em vào các DAO
        ProvinceDAO provinceDAO = new ProvinceDAO(em);
        DistrictDAO districtDAO = new DistrictDAO(em);
        WardDAO wardDAO = new WardDAO(em);

        try {
            tx.begin();

            // Xóa dữ liệu cũ nếu muốn full sync
            provinceDAO.deleteAll();
            districtDAO.deleteAll();
            wardDAO.deleteAll();

            for (ProvinceExternalDTO p : provinceExternalList) {
                Province province = new Province();
                province.setId(Integer.parseInt(p.code));
                province.setName(p.name);
                provinceDAO.save(province);

                for (DistrictExternalDTO d : p.districts) {
                    District district = new District();
                    district.setId(Integer.parseInt(d.code));
                    district.setName(d.name);
                    // Lấy luôn province đã tạo ở trên
                    district.setProvince(province);
                    districtDAO.save(district);

                    for (WardExternalDTO w : d.wards) {
                        Ward ward = new Ward();
                        ward.setId(Integer.parseInt(w.code));
                        ward.setName(w.name);
                        // Lấy luôn district đã tạo ở trên
                        ward.setDistrict(district);
                        wardDAO.save(ward);
                    }
                }
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<LocationDTO> getProvinces() {
        EntityManager em = JpaUtil.getEntityManager();
        ProvinceDAO  provinceDAO = new ProvinceDAO(em);
        return provinceDAO.findAll().stream()
                .map(p -> new LocationDTO(p.getId(), p.getName()))
                .collect(Collectors.toList());
    }

    public List<LocationDTO> getDistricts(int provinceId) {
        EntityManager em = JpaUtil.getEntityManager();
        DistrictDAO districtDAO = new DistrictDAO(em);
        return districtDAO.getDistrictsByProvinceId(provinceId).stream()
                .map(d -> new LocationDTO(d.getId(), d.getName()))
                .collect(Collectors.toList());
    }

    public List<LocationDTO> getWards(int districtId) {
        EntityManager em = JpaUtil.getEntityManager();
        WardDAO wardDAO = new WardDAO(em);
        return wardDAO.findWardsByDistrictId(districtId).stream()
                .map(w -> new LocationDTO(w.getId(), w.getName()))
                .collect(Collectors.toList());
    }

    public String getAddressForAPI(int districtId) {
        EntityManager em = JpaUtil.getEntityManager();
        DistrictDAO districtDAO = new DistrictDAO(em);
        District district = districtDAO.findById(districtId);
        return district.getName() + ", " + district.getProvince().getName() + ", Vietnam";
    }
    public String getOnlyProvinceForAPI(int districtId) {
        EntityManager em = JpaUtil.getEntityManager();
        DistrictDAO districtDAO = new DistrictDAO(em);
        District district = districtDAO.findById(districtId);
        return district.getProvince().getName() + ", Vietnam";
    }
}
