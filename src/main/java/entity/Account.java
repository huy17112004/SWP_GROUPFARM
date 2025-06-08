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

    @Column(name = "UserName", nullable = false, length = 50)
    private String userName;

    @Column(name = "Password", nullable = false, length = 50)
    private String password;
}
