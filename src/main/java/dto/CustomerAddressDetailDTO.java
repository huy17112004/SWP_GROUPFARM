package dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressDetailDTO {
    private int id;
    private String street;
    private Float latitude;
    private Float longitude;

    private Integer wardID;
    private Integer districtID;
    private Integer provinceID;

}
