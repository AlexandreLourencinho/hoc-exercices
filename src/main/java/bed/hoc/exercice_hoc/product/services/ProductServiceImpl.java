package bed.hoc.exercice_hoc.product.services;

import bed.hoc.exercice_hoc.product.dto.ProductDTOCreate;
import bed.hoc.exercice_hoc.product.dto.ProductDTOGet;
import bed.hoc.exercice_hoc.product.dto.ProductDTOStock;
import bed.hoc.exercice_hoc.product.dto.ProductDTOUpdate;
import bed.hoc.exercice_hoc.product.entity.ProductEntity;
import bed.hoc.exercice_hoc.product.exceptions.ProductNotFoundException;
import bed.hoc.exercice_hoc.product.mapper.ProductToDTOMapper;
import bed.hoc.exercice_hoc.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductDTOGet getProduct(int id) {
        return ProductToDTOMapper.entityToDTOget(this.findProductOrElseThrow(id));
    }

    @Override
    public ProductDTOStock getProductStock(int id) {
        return ProductToDTOMapper.entityToDTOStock(this.findProductOrElseThrow(id));
    }

    @Override
    public List<ProductDTOGet> getProducts(List<Integer> ids) {
        return this.repository.findAllById(ids).stream().map(ProductToDTOMapper::entityToDTOget).toList();
    }

    @Override
    public List<ProductDTOGet> getProducts() {
        return this.repository.findAll().stream().map(ProductToDTOMapper::entityToDTOget).toList();
    }

    @Override
    public ProductDTOGet createProduct(ProductDTOCreate dto) {
        return ProductToDTOMapper.entityToDTOget(this.repository.save(ProductToDTOMapper.dtoCreateToEntity(dto)));
    }

    @Override
    public ProductDTOGet updateProduct(ProductDTOUpdate dto) {
        var entity = this.findProductOrElseThrow(dto.getId());
        ProductToDTOMapper.dtoUpdateToEntity(dto, entity);
        return ProductToDTOMapper.entityToDTOget(this.repository.save(entity));
    }

    @Override
    public void deleteProduct(int id) {
        this.repository.deleteById(id);
    }

    @Override
    public void deleteProducts(List<Integer> ids) {
        this.repository.deleteAllById(ids);
    }

    private ProductEntity findProductOrElseThrow(int id) {
        return this.repository.findById(id).orElseThrow(() -> {
            log.error("Product with id {} wasn't retrieved in database", id);
            return new ProductNotFoundException();
        });
    }

}
