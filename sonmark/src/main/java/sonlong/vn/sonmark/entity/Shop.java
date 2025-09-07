package sonlong.vn.sonmark.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id ;

    @Column(name = "name")
    private String name ;

    @Column(name = "description")
    private String desc ;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private ShopStatus status  = ShopStatus.ACTIVE;

    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    @PrePersist
    public void updateTimestamp() {
        updatedAt = LocalDateTime.now();
    }

    @Column(name = "create_at")
    private LocalDateTime createAt ;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user ;

    @OneToMany(mappedBy = "shop" , cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

}
