package dao;
import dto.WarehouseDetailViewDTO;
import entity.Warehouse;
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
}
