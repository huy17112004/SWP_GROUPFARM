package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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
    private int id;

    @Column(name = "RiskType", length = 50)
    private String riskType;

    @Column(name = "Description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "ReportedBy")
    private String reportedBy;

    @Column(name = "ReportedAt")
    private Date reportedAt;

    @Column(name = "IsResolved", columnDefinition = "BIT")
    private boolean isResolved;

    @Column(name = "ResolvedAt")
    private Date resolvedAt;

    @Column(name = "ResolutionNote", columnDefinition = "NVARCHAR(MAX)")
    private String resolutionNote;

    /* n OrderRisk ↔ 1 WholesaleOrder */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderID", nullable = false, insertable = false, updatable = false)
    private WholesaleOrder order;
}