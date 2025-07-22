// src/main/java/service/WarehouseManagerService.java
package service;

import dao.WarehouseManagerDAO;
import dto.WarehouseManagerDTO;
import entity.WarehouseManager;
import util.JpaUtil;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class WarehouseManagerService {

    /**
     * Lấy danh sách WarehouseManager và chuyển thành DTO
     */
    public List<WarehouseManagerDTO> getAllManagers() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            List<WarehouseManager> list = new WarehouseManagerDAO(em).findAll();
            return list.stream()
                    .map(m -> new WarehouseManagerDTO(
                            m.getId(), m.getName(), m.getEmail(), m.getPhone()
                    )).collect(Collectors.toList());
        } finally {
            em.close();
        }
    }
}
