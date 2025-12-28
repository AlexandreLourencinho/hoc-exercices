package bed.hoc.exercice_hoc.product.mapper;

import bed.hoc.exercice_hoc.product.dto.ProductDTOCreate;
import bed.hoc.exercice_hoc.product.dto.ProductDTOUpdate;
import bed.hoc.exercice_hoc.product.entity.ProductEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductToDTOMapperTest {

    @Test
    void entityToDTOget() {
        var entity = new ProductEntity(2, "name", "description", new BigDecimal("50.33"), 10, true);

        var result = ProductToDTOMapper.entityToDTOget(entity);

        assertThat(result).usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void dtoCreateToEntity() {
        var dto = new ProductDTOCreate("name", "description", new BigDecimal("50.33"), 10, false);

        var result = ProductToDTOMapper.dtoCreateToEntity(dto);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dto);
    }

    @Test
    void entityToDTOStock() {
        var entity = new ProductEntity(2, "name", "description", new BigDecimal("50.33"), 10, true);

        var result = ProductToDTOMapper.entityToDTOStock(entity);

        assertEquals(entity.getStockQuantity(), result.getStockQuantity());
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