package bed.hoc.exercice_hoc.product.services;

import bed.hoc.exercice_hoc.product.dto.ProductDTOCreate;
import bed.hoc.exercice_hoc.product.dto.ProductDTOGet;
import bed.hoc.exercice_hoc.product.dto.ProductDTOStock;
import bed.hoc.exercice_hoc.product.dto.ProductDTOUpdate;

import java.util.List;

public interface ProductService {

    ProductDTOGet getProduct(int id);
    ProductDTOStock getProductStock(int id);
    List<ProductDTOGet> getProducts(List<Integer> ids);
    List<ProductDTOGet> getProducts();
    ProductDTOGet createProduct(ProductDTOCreate dto);
    ProductDTOGet updateProduct(ProductDTOUpdate dto);
    void deleteProduct(int id);
    void deleteProducts(List<Integer> ids);

}
