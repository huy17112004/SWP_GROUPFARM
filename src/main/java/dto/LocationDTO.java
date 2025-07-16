package dto;

import entity.Province;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    int locationId;
    String locationName;
}
