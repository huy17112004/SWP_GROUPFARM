package service;

import dao.WarehouseDetailViewDAO;
import dto.WarehouseDetailRequestDTO;
import dto.WarehouseDetailViewDTO;
import jakarta.persistence.EntityTransaction;
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
    public boolean createDetail(WarehouseDetailRequestDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            WarehouseDetailViewDAO dao = new WarehouseDetailViewDAO(em);
            dao.create(dto);
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

    public boolean updateDetail(int id, WarehouseDetailRequestDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            WarehouseDetailViewDAO dao = new WarehouseDetailViewDAO(em);
            boolean ok = dao.update(id, dto);
            tx.commit();
            return ok;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean deleteDetail(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            WarehouseDetailViewDAO dao = new WarehouseDetailViewDAO(em);
            boolean ok = dao.delete(id);
            tx.commit();
            return ok;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}