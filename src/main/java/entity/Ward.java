package entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Ward")
public class Ward {

    @Id
    @Column(name = "WardID")
    private int id;

    @Column(name = "WardName", columnDefinition = "NVARCHAR(50)", nullable = false, length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DistrictID", nullable = false)
    private District district;

    @OneToMany(mappedBy = "ward", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addresses;
}
