package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCheckRequestDTO {
    private String deliveryDate; // yyyy-MM-dd'T'HH:mm:ss
    private int addressId;
}