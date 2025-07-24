package dto;

import entity.StockTransfer;
import entity.StockTransferItem;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class StockTransferRequestDTO {
    int stockTransferId;
    int orderId;
    int sourceWarehouseId;
    int destinationWarehouseId;
    String sourceWarehouseName;
    String destinationWarehouseName;
    String sourceWarehouseAddress;
    String destinationWarehouseAddress;
    String status;
    LocalDateTime requestedAt;
    LocalDateTime completedAt;
    List<StockTransferItemDTO> stockTransferItems;

    public StockTransferRequestDTO(StockTransfer stockTransfer) {
        this.stockTransferId = stockTransfer.getId();
        this.orderId = stockTransfer.getOrder().getId();
        this.sourceWarehouseId = stockTransfer.getSourceWarehouse().getId();
        this.destinationWarehouseId = stockTransfer.getDestinationWarehouse().getId();
        this.sourceWarehouseName = stockTransfer.getSourceWarehouse().getWarehouseName();
        this.destinationWarehouseName = stockTransfer.getDestinationWarehouse().getWarehouseName();
        this.sourceWarehouseAddress = stockTransfer.getSourceWarehouse().getAddress().toStringAddress();
        this.destinationWarehouseAddress = stockTransfer.getDestinationWarehouse().getAddress().toStringAddress();
        this.status = stockTransfer.getStatus();
        this.requestedAt = stockTransfer.getRequestedAt();
        this.completedAt = stockTransfer.getCompletedAt();
        this.stockTransferItems = stockTransfer.getItems().stream().map(StockTransferItemDTO::new).collect(Collectors.toList());
    }

    @Getter
    @Setter
    class StockTransferItemDTO {
        int stockTransferItemId;
        int stockLotId;
        String productName;
        int quantity;

        StockTransferItemDTO(StockTransferItem stockTransferItem) {
            this.stockTransferItemId = stockTransferItem.getId();
            this.stockLotId = stockTransferItem.getStockLot().getId();
            this.productName = stockTransferItem.getStockLot().getProduct().getProductName();
            this.quantity = stockTransferItem.getQuantity();
        }
    }
}
