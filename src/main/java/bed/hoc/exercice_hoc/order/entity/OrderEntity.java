package bed.hoc.exercice_hoc.order.entity;

import bed.hoc.exercice_hoc.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items = new ArrayList<>();

    public BigDecimal getTotalPrice() {
        return this.items.stream()
                .map(predicate -> predicate.getProduct().getPrice().multiply(BigDecimal.valueOf(predicate.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
