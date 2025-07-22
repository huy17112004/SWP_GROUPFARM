package dto;

import entity.WholesaleOrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class StockLotAllocationDTO {
    int orderItemId;
    String productName;
    int quantity;
    List<AllocationInformation> allocationList;

    public StockLotAllocationDTO(WholesaleOrderItem wholesaleOrderItem) {
        this.orderItemId = wholesaleOrderItem.getId();
        this.productName = wholesaleOrderItem.getProduct().getProductName();
        this.quantity = wholesaleOrderItem.getQuantity();
        allocationList = new ArrayList<>();

    }


    @Getter
    @Setter
    @AllArgsConstructor
    public static class AllocationInformation {
        int stockLotId;
        int takenQuantity;
        int totalQuantity;
        Date expiredDate;
        String warehouseName;

    }


}
