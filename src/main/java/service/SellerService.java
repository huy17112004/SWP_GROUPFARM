package service;

import dao.SellerDAO;
import dao.WholesaleOrderDAO;
import entity.Seller;
import jakarta.persistence.EntityManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SellerService {
    public Seller selectBestSeller(EntityManager em) {

        WholesaleOrderDAO wholesaleOrderDAO = new WholesaleOrderDAO(em);
        SellerDAO sellerDAO = new SellerDAO(em);

        List<Seller> activeSellers = sellerDAO.getActiveSellers();

        if (activeSellers.isEmpty()) {
            throw new RuntimeException("Không có seller nào đang hoạt động.");
        }

        Map<Integer, Long> negotiatingMap = wholesaleOrderDAO.getNegotiatingCountBySeller();

        // Map<User, Long>
        Map<Seller, Long> sellerCountMap = new HashMap<>();
        for (Seller seller : activeSellers) {
            long count = negotiatingMap.getOrDefault(seller.getId(), 0L);
            sellerCountMap.put(seller, count);
        }

        // Tìm min
        long minCount = sellerCountMap.values().stream().min(Long::compare).orElse(0L);

        // Lọc seller có số lượng bằng minCount
        List<Seller> bestSellers = sellerCountMap.entrySet().stream()
                .filter(entry -> entry.getValue() == minCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Trả về 1 người ngẫu nhiên trong nhóm ít đơn nhất
        Collections.shuffle(bestSellers);
        return bestSellers.get(0);
    }

}
