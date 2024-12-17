package com.POS_Store.App.Controller;

import com.POS_Store.App.Repo.ProductsRepo;
import com.POS_Store.App.models.ProductsM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/Products")
public class ProductsController {

    private final ProductsRepo productsRepo;
    public ProductsController(ProductsRepo productsRepo) {
        this.productsRepo = productsRepo;
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductsM>> getProduct() {
        List<ProductsM> products = productsRepo.findAll();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductsM product) {
        try{
            if (product.getType() != null && product.getType().getParentCategory() != product.getCategory())
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid subcategory " + product.getType() +
                                " for category " + product.getCategory());
            }

            ProductsM newProduct = productsRepo.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating product: " + e.getMessage());
        }
    }

}
