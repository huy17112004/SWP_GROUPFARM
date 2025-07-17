package dao;
import dto.WarehouseDetailRequestDTO;
import dto.WarehouseDetailViewDTO;
import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class WarehouseDetailViewDAO {

    private final EntityManager em;

    public WarehouseDetailViewDAO(EntityManager em) {
        this.em = em;
    }

    public List<WarehouseDetailViewDTO> findAllDetailView() {
        String jpql =
                "SELECT new dto.WarehouseDetailViewDTO(" +
                        "w.id, w.warehouseName, w.warehousePhone, " +
                        "CONCAT(a.street, ', ', ward.name, ', ', d.name, ', ', p.name), " +
                        "wm.name, ws.name) " +
                        "FROM Warehouse w " +
                        "JOIN w.address a " +
                        "JOIN a.ward ward " +
                        "JOIN ward.district d " +
                        "JOIN d.province p " +
                        "LEFT JOIN w.warehouseManager wm " +
                        "LEFT JOIN w.warehouseStaff ws";
        TypedQuery<WarehouseDetailViewDTO> q = em.createQuery(jpql, WarehouseDetailViewDTO.class);
        return q.getResultList();
    }

    public WarehouseDetailViewDTO findDetailViewById(int id) {
        String jpql =
                "SELECT new dto.WarehouseDetailViewDTO(" +
                        "w.id, w.warehouseName, w.warehousePhone, " +
                        "CONCAT(a.street, ', ', ward.name, ', ', d.name, ', ', p.name), " +
                        "wm.name, ws.name) " +
                        "FROM Warehouse w " +
                        "JOIN w.address a " +
                        "JOIN a.ward ward " +
                        "JOIN ward.district d " +
                        "JOIN d.province p " +
                        "LEFT JOIN w.warehouseManager wm " +
                        "LEFT JOIN w.warehouseStaff ws " +
                        "WHERE w.id = :id";
        TypedQuery<WarehouseDetailViewDTO> q = em.createQuery(jpql, WarehouseDetailViewDTO.class);
        q.setParameter("id", id);
        return q.getResultStream().findFirst().orElse(null);
    }
    public void create(WarehouseDetailRequestDTO dto) {
        // 1. Lấy đối tượng Address (đã tồn tại) hoặc tạo mới
        Ward ward = em.find(Ward.class, dto.getWardId());
        Address addr = new Address();
        addr.setStreet(dto.getStreet());
        addr.setWard(ward);
        em.persist(addr);

        // 2. Tạo Warehouse
        Warehouse w = new Warehouse();
        w.setWarehouseName(dto.getWarehouseName());
        w.setWarehousePhone(dto.getWarehousePhone());
        w.setAddress(addr);

        // 3. Gắn manager và staff nếu có
        if (dto.getManagerId()!=null) {
            WarehouseManager wm = em.find(WarehouseManager.class, dto.getManagerId());
            w.setWarehouseManager(wm);
        }
        if (dto.getStaffId()!=null) {
            WarehouseStaff ws = em.find(WarehouseStaff.class, dto.getStaffId());
            w.setWarehouseStaff(ws);
        }

        em.persist(w);
    }

    public boolean update(int id, WarehouseDetailRequestDTO dto) {
        Warehouse w = em.find(Warehouse.class, id);
        if (w == null) return false;

        w.setWarehouseName(dto.getWarehouseName());
        w.setWarehousePhone(dto.getWarehousePhone());
        // Cập nhật Address
        Address addr = w.getAddress();
        addr.setStreet(dto.getStreet());
        // Nếu thay ward
        if (addr.getWard().getId() != dto.getWardId()) {
            Ward newWard = em.find(Ward.class, dto.getWardId());
            addr.setWard(newWard);
        }
        // Manager / Staff
        w.setWarehouseManager(dto.getManagerId() != null
                ? em.find(WarehouseManager.class, dto.getManagerId())
                : null);
        w.setWarehouseStaff(dto.getStaffId() != null
                ? em.find(WarehouseStaff.class, dto.getStaffId())
                : null);

        em.merge(addr);
        em.merge(w);
        return true;
    }

    public boolean delete(int id) {
        Warehouse w = em.find(Warehouse.class, id);
        if (w == null) return false;
        em.remove(w);
        return true;
    }
}
