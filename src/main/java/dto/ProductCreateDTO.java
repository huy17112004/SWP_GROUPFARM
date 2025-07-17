package dto;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDTO {
    private String productName;
    private int entryPrice;
    private int retailPrice;
    private BigDecimal wholesalePrice;
    private String description;
    private int categoryId;
    private List<String> imageUrls;
}