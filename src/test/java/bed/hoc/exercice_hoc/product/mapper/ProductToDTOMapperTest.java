package bed.hoc.exercice_hoc.product.mapper;

import bed.hoc.exercice_hoc.product.dto.ProductDTOUpdate;
import bed.hoc.exercice_hoc.product.entity.ProductEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProductToDTOMapperTest {

    @Test
    void entityToDTOget() {
    }

    @Test
    void dtoCreateToEntity() {
    }

    @Test
    void entityToDTOStock() {
    }

    @Test
    void dtoUpdateToEntity() {
        var dto = new ProductDTOUpdate(2, "name", "description", new BigDecimal("50.33"), 10, true);
        var entity = new ProductEntity(2, "name2", "description2", new BigDecimal("520.33"), 120, false);

        ProductToDTOMapper.dtoUpdateToEntity(dto, entity);

        assertThat(entity)
                .usingRecursiveComparison()
                .isEqualTo(dto);
    }

}