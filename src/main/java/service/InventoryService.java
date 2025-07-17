package service;

import dto.LotImportRequestDTO;
import entity.StockLot;
import entity.StockLotAudit;
import entity.Product;
import entity.Warehouse;
import entity.WarehouseStaff;
import util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class InventoryService {
    public void importLots(LotImportRequestDTO dto, int staffId) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // 1. Validate tồn tại product và staff
            Product product = em.find(Product.class, dto.getProductId());
            if (product == null) throw new IllegalArgumentException("Product không tồn tại: " + dto.getProductId());
            WarehouseStaff staff = em.find(WarehouseStaff.class, staffId);
            if (staff == null) throw new IllegalArgumentException("Staff không tồn tại: " + staffId);

            // 2. Tạo từng lô
            for (LotImportRequestDTO.LotInput lot : dto.getLots()) {
                Warehouse wh = em.find(Warehouse.class, lot.getWarehouseId());
                if (wh == null) throw new IllegalArgumentException("Warehouse không tồn tại: " + lot.getWarehouseId());

                StockLot sl = new StockLot();
                sl.setProduct(product);
                sl.setWarehouse(wh);
                sl.setQuantity(lot.getQuantity());

                // Parse importDate
                LocalDate ldImport = LocalDate.parse(lot.getImportDate());
                sl.setImportDate(Date.valueOf(ldImport));

                // Parse expiredDate nếu có
                if (lot.getExpiredDate() != null && !lot.getExpiredDate().isBlank()) {
                    LocalDate ldExp = LocalDate.parse(lot.getExpiredDate());
                    sl.setExpiredDate(Date.valueOf(ldExp));
                } else {
                    sl.setExpiredDate(null);
                }

                em.persist(sl);

                // Audit
                StockLotAudit audit = new StockLotAudit();
                audit.setStockLot(sl);
                audit.setBeforeQty(0);
                audit.setChangeQty(lot.getQuantity());
                audit.setAfterQty(lot.getQuantity());
                audit.setActionType("IMPORT");
                audit.setPerformedBy(staff);
                audit.setActionTime(LocalDateTime.now());
                em.persist(audit);
            }

            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
