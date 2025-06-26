package service;

import dao.WarehouseDetailViewDAO;
import dto.WarehouseDetailViewDTO;
import util.JpaUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class WarehouseDetailViewService {

    public List<WarehouseDetailViewDTO> getAllDetailView() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            WarehouseDetailViewDAO dao = new WarehouseDetailViewDAO(em);
            return dao.findAllDetailView();
        } finally {
            em.close();
        }
    }

    public WarehouseDetailViewDTO getDetailViewById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            WarehouseDetailViewDAO dao = new WarehouseDetailViewDAO(em);
            return dao.findDetailViewById(id);
        } finally {
            em.close();
        }
    }
}