package dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageDTO {
    private int id;
    private String content;
    private LocalDateTime createdAt;
    private String senderType;
    private String senderName;
    private int orderId;
    private boolean isSender;
}
