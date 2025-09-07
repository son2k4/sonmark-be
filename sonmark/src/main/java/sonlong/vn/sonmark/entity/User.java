package sonlong.vn.sonmark.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.service.spi.InjectService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id ;

        @Column(name = "username")
        private String username ;

        @Column(name = "email")
        private String email ;

        @Column(name = "password")
        private String password ;

        @Column(name = "create_at")
        private LocalDateTime createAt ;

        @Column(name = "status")
        private boolean status ;

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
        private Set<Role> roles = new HashSet<>();

        @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
        private Shop shop ;
}
