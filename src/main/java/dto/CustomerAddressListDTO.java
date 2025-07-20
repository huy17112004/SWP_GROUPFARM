package dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressListDTO {
    private int id; // customerAddressID
    private String street;
    int wardId;
    int districtId;
    int provinceId;
    private String wardName;
    private String districtName;
    private String provinceName;
    private float latitude;
    private float longitude;
}
