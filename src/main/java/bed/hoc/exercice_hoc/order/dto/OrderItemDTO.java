package bed.hoc.exercice_hoc.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderItemDTO {

    @NotNull
    private Integer id;
    @NotNull
    private Integer productId;
    @NotNull
    private Integer quantity;

}
