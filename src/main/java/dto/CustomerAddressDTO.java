package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressDTO {
    private String street;
    private int wardID;
    private float latitude;
    private float longitude;
}
