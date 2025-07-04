package dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressListDTO {
    private int id; // customerAddressID
    private String street;
    private String wardName;
    private String districtName;
    private String provinceName;
}
