package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderRequestDTO {
    private int customerId;
    private int addressId; // hoặc addressInfo nếu cần
    private BigDecimal estimatedShipFee;
    // Nếu muốn truyền cả cart thì thêm:
    private List<CartItemDTO> cartItems;
}
