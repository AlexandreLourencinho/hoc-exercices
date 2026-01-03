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
        return null;
    }

    public static ProductEntity dtoCreateToEntity(ProductDTOCreate dto) {
        return null;
    }

    public static ProductDTOStock entityToDTOStock(ProductEntity entity) {
        return null;
    }

    public static void dtoUpdateToEntity(ProductDTOUpdate dto, ProductEntity entity) {
        entity.setActive(dto.getActive())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setPrice(dto.getPrice())
                .setStockQuantity(dto.getStockQuantity());
    }

}
