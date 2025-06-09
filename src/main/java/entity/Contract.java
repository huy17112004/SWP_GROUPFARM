package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContractID")
    private int id;

    @Column(name = "PDFUrl", length = 255)
    private String pdfUrl;

    @Column(name = "SignedByCustomer", columnDefinition = "BIT", nullable = false)
    private boolean signedByCustomer;

    @Column(name = "SignedAt")
    private LocalDateTime signedAt;

    @Column(name = "Log", columnDefinition = "NVARCHAR(MAX)")
    private String log;

    @Column(name = "CreateAt")
    private LocalDateTime createAt;

    @Column(name = "Status", length = 255)
    private String status;

    /* 1 Customer ↔ n Contract */
    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false, insertable = false, updatable = false)
    private WholesaleCustomer customer;

    /* 1 Contract ↔ n WholesaleOrder */
    @OneToOne(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private WholesaleOrder wholesaleOrders;
}