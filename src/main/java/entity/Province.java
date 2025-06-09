package entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Province")
public class Province {

    @Id
    @Column(name = "ProvinceID")
    private int id;

    @Column(name = "ProvinceName", nullable = false, length = 255)
    private String provinceName;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<District> districts;
}
