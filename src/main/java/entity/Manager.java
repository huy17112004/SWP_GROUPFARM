package entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Manager")
public class Manager extends Account {

    @Column(name = "Name", nullable = false)
    private String name;

    // Getters & Setters...
}