package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ContractTemplate")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false, unique = true, length = 100)
    private String name;

    @Lob
    @Column(name = "ContentHtml", nullable = false)
    private String contentHtml;

    @Column(name = "UpdatedAt", nullable = false)
    private LocalDateTime updatedAt;

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
        this.updatedAt = LocalDateTime.now();
    }

    public ContractTemplate(String name, String contentHtml) {
        this.name = name;
        this.contentHtml = contentHtml;
        this.updatedAt = LocalDateTime.now();
    }
}
