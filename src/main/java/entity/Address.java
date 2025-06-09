package entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AddressID")
    private int id;

    @Column(name = "Street", nullable = false, length = 255)
    private String street;

    @Column(name = "Latitude")
    private Float latitude;

    @Column(name = "Longitude")
    private Float longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WardID", nullable = false)
    private Ward ward;

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private Warehouse warehouse;

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private CustomerAddress customerAddresses;
}
