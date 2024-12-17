package com.POS_Store.App.Repo;

import com.POS_Store.App.models.ProductsM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepo extends JpaRepository<ProductsM, Long> {

}
