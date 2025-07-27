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

    @Column(name = "Name", columnDefinition = "NVARCHAR(50)", nullable = false)
    private String name;

}