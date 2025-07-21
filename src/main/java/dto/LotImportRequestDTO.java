package dto;

import java.util.List;

public class LotImportRequestDTO {
    private Integer productId;      // ID sản phẩm đã tồn tại
    private List<LotInput> lots;    // danh sách lô nhập

    // getters / setters omitted for brevity

    public static class LotInput {
        private Integer quantity;       // số lượng
        private String importDate;      // "yyyy-MM-dd"
        private String expiredDate;     // "yyyy-MM-dd" hoặc null
        private Integer warehouseId;    // kho nhập

        // getters và setters
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public String getImportDate() { return importDate; }
        public void setImportDate(String importDate) { this.importDate = importDate; }
        public String getExpiredDate() { return expiredDate; }
        public void setExpiredDate(String expiredDate) { this.expiredDate = expiredDate; }
        public Integer getWarehouseId() { return warehouseId; }
        public void setWarehouseId(Integer warehouseId) { this.warehouseId = warehouseId; }
    }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public List<LotInput> getLots() { return lots; }
    public void setLots(List<LotInput> lots) { this.lots = lots; }
}
