package dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String message;
    private boolean success;
    private String accountType; // ADMIN, SALER, MANAGER, SHIPPER, WAREHOUSE_MANAGER, WHOLESALE_CUSTOMER
    private int accountId;
    private String username;
    private String name; // Tên người dùng (nếu có)
}