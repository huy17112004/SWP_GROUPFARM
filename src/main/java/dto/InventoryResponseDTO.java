package dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InventoryResponseDTO {
    private int productId;
    private String productName;
    private int totalQuantity;
}