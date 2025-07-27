package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MessageID")
    private int id;

    @Column(name = "Content", nullable = false, columnDefinition = "NVARCHAR(200)")
    private String content;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    // Người gửi là Seller hay Customer?
    @Column(name = "SenderType", nullable = false)
    private String senderType; // "SELLER" hoặc "CUSTOMER"

    // Liên kết đến Customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID", nullable = false)
    private WholesaleCustomer customer;

    // Liên kết đến Seller
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SellerID", nullable = false)
    private Seller seller;

    // Gắn với đơn hàng cụ thể
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WholesaleOrderID", nullable = false)
    private WholesaleOrder order;
}
