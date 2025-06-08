package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OrderRisk")
public class OrderRisk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RiskID")
    private int riskID;

    @Column(name = "OrderID", nullable = false)
    private int orderID;

    @Column(name = "RiskType", length = 50)
    private String riskType;

    @Column(name = "Description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "ReportedBy")
    private int reportedBy;

    @Column(name = "ReportedAt")
    private LocalDateTime reportedAt;

    @Column(name = "IsResolved", columnDefinition = "BIT")
    private boolean isResolved;

    @Column(name = "ResolvedAt")
    private LocalDateTime resolvedAt;

    @Column(name = "ResolutionNote", columnDefinition = "NVARCHAR(MAX)")
    private String resolutionNote;

    /* n OrderRisk ↔ 1 WholesaleOrder */
    @ManyToOne
    @JoinColumn(name = "OrderID", nullable = false, insertable = false, updatable = false)
    private WholesaleOrder order;
}