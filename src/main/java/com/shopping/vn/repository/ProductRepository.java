package com.shopping.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopping.vn.entity.Product;
import com.shopping.vn.repository.customer.ProductRepositoryCustomer;

public interface ProductRepository extends ProductRepositoryCustomer, JpaRepository<Product, Long>{

}
