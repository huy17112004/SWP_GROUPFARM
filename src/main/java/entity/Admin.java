package entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Admin")
public class Admin extends Account {
    public Admin(int accountId, String username, String password) {
        super(accountId, username, password);
    }
}