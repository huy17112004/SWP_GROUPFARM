package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponseDTO {
    private String imageUrl;
    private String name;
    private Integer retailPrice;
    private String categoryName;
    private String warehouseName;
    private Integer  lotId;
    private Integer quantity;
    private Date expiredDate;   // đổi từ LocalDateTime → Date
    private Date importDate;
}
