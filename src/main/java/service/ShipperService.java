package service;

import dao.ShipperDAO;
import dto.ShipperNameDTO;
import entity.Shipper;
import entity.WholesaleOrder;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

import java.util.List;
import java.util.stream.Collectors;

public class ShipperService {
    public List<ShipperNameDTO> getShippers() {
        EntityManager em = JpaUtil.getEntityManager();
        ShipperDAO shipperDAO = new ShipperDAO(em);
        return shipperDAO.findAll().stream().map(ShipperNameDTO::new).collect(Collectors.toList());
    }
}
