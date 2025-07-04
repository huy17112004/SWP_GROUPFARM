package entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Manager")
public class Manager extends Account {

    @Column(name = "Name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manager")
    private List<DealRequest> dealRequests;
}