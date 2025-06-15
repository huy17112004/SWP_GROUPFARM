package entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")
    private long id;

    @Column(name = "Username", nullable = false, length = 50)
    private String username;

    @Column(name = "Password", nullable = false, length = 100)
    private String password;


}
