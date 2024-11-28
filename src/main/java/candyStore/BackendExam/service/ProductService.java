package candyStore.BackendExam.service;

import candyStore.BackendExam.model.Product;
import candyStore.BackendExam.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }
    public Product saveProduct(Product product){
        return productRepo.save(product);
    }
    public List<Product> getProducts() {
        return productRepo.findAll();
    }
}
