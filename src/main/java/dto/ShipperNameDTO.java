package dto;

import entity.Shipper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipperNameDTO {
    int shipperId;
    String shipperName;

    public ShipperNameDTO(Shipper shipper) {
        this.shipperId = shipper.getId();
        this.shipperName = shipper.getName();
    }
}
