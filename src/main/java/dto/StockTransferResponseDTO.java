package dto;

import entity.StockTransfer;
import entity.StockTransferItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class StockTransferResponseDTO {
    public int stockTransferId;
    public int sourceWarehouseId;
    public String sourceWarehouseName;
    public int destinationWarehouseId;
    public String destinationWarehouseName;
    public String status;
    public LocalDateTime requestedAt;
    public List<StockTransferItemResponseDTO> items;
    public StockTransferResponseDTO(StockTransfer stockTransfer) {
        this.stockTransferId = stockTransfer.getId();
        this.sourceWarehouseId = stockTransfer.getSourceWarehouse().getId();
        this.sourceWarehouseName = stockTransfer.getSourceWarehouse().getWarehouseName();
        this.destinationWarehouseId = stockTransfer.getDestinationWarehouse().getId();
        this.destinationWarehouseName = stockTransfer.getDestinationWarehouse().getWarehouseName();
        this.status = stockTransfer.getStatus();
        this.requestedAt = stockTransfer.getRequestedAt();
        this.items = stockTransfer.getItems().stream().map(StockTransferItemResponseDTO::new).collect(Collectors.toList());
    }

    public static class StockTransferItemResponseDTO {
        public int stockTransferItemId;
        public int stockLotId;
        public String productName;
        public int quantity;
        public StockTransferItemResponseDTO(StockTransferItem stockTransferItem) {
            this.stockTransferItemId = stockTransferItem.getId();
            this.stockLotId = stockTransferItem.getStockLot().getId();
            this.productName = stockTransferItem.getStockLot().getProduct().getProductName();
            this.quantity = stockTransferItem.getQuantity();
        }
    }
}