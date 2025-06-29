package dto;

import lombok.Data;

@Data
public class MessageRequestDTO {
    private String content;
    private int orderId;
}
