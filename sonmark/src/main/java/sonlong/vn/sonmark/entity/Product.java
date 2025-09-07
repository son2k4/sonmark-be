package sonlong.vn.sonmark.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id ;

    @Column(name = "name")
    private String name ;

    @Column(name = "description")
    private String desc ;

    @Column(name = "list_price")
    private double listPrice ;

    @Column(name = "sell_price")
    private double sellPrice ;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category ;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH})
    @JoinColumn(name = "shop_id")
    private Shop shop ;


}
