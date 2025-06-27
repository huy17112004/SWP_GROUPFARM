package dto;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WholeSaleCustomerDTO {

    private int Id;
    private String contactPerson;
    private String email;
    private String phone;
    private String companyName;
    private String taxCode;
    private String businessType;
    private String status;
    private Integer customerAddressId;
    private Integer addressId;
    private String street;
    private Float latitude;
    private Float longitude;
    private Integer wardId;
}