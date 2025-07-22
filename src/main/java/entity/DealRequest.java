package entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DealRequest")
public class DealRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DealRequestID")
    private int id;

    // Nếu bạn deal theo từng item
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderItemID", nullable = false)
    private WholesaleOrderItem orderItem;


    @Column(name = "ProposedPrice", precision = 18, scale = 2, nullable = false)
    private BigDecimal proposedPrice;

    @Column(name = "Status", length = 20, nullable = false)
    private String status; // PENDING, APPROVED, REJECTED

    @Column(name = "RequestedAt", nullable = false)
    private LocalDateTime requestedAt;

    @Column(name = "RespondedAt")
    private LocalDateTime respondedAt;

    @Column(name = "Message", columnDefinition = "TEXT")
    private String message;
}