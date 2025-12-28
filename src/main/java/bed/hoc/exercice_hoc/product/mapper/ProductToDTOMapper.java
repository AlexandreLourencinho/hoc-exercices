package bed.hoc.exercice_hoc.product.mapper;

import bed.hoc.exercice_hoc.product.dto.ProductDTOCreate;
import bed.hoc.exercice_hoc.product.dto.ProductDTOGet;
import bed.hoc.exercice_hoc.product.dto.ProductDTOStock;
import bed.hoc.exercice_hoc.product.dto.ProductDTOUpdate;
import bed.hoc.exercice_hoc.product.entity.ProductEntity;

import static bed.hoc.exercice_hoc.common.constants.CommonConstants.throwUtilityClassException;

public class ProductToDTOMapper {

    private ProductToDTOMapper() {
        throwUtilityClassException();
    }

    public static ProductDTOGet entityToDTOget(ProductEntity entity) {
        return new ProductDTOGet(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice(), entity.getStockQuantity(), entity.isActive());
    }

    public static ProductEntity dtoCreateToEntity(ProductDTOCreate dto) {
        return new ProductEntity(null, dto.getName(), dto.getDescription(), dto.getPrice(), dto.getStockQuantity(), Boolean.TRUE.equals(dto.getActive()));
    }

    public static ProductDTOStock entityToDTOStock(ProductEntity entity) {
        return new ProductDTOStock(entity.getStockQuantity());
    }

    public static void dtoUpdateToEntity(ProductDTOUpdate dto, ProductEntity entity) {
        entity.setActive(dto.getActive())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setPrice(dto.getPrice())
                .setStockQuantity(dto.getStockQuantity());
    }

}
