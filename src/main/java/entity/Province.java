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

    @Column(name = "ProvinceName",columnDefinition = "NVARCHAR(50)", nullable = false, length = 255)
    private String name;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<District> districts;
}
