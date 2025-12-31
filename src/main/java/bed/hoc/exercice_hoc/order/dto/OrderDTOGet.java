package bed.hoc.exercice_hoc.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderDTOGet {

    private Integer id;
    private Integer userId;
    private List<OrderItemDTO> items = new ArrayList<>();
    private BigDecimal totalPrice;

}
