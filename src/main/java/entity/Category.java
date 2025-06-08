package entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID")
    private int categoryID;

    @Column(name = "CategoryName", length = 255, nullable = false)
    private String categoryName;

    @Column(name = "ShelfLifeDays", nullable = false)
    private int shelfLifeDays; // Số ngày hạn sử dụng, ví dụ 2 hoặc 3 ngày

    /* 1 Category ↔ n Product */
    @OneToMany(mappedBy = "categoryId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;
}