package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Saler")
public class Saler extends Account {

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Column(name = "Phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "CreateAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "Status", nullable = false, length = 50)
    private String status;

    // Getters & Setters...
}
