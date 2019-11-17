package com.shopping.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.shopping.vn.entity.Product;
import com.shopping.vn.repository.customer.ProductRepositoryCustomer;

public interface ProductRepository extends ProductRepositoryCustomer, JpaRepository<Product, Long> {
  
  @Query(value="select p.id,p.status from product p where p.id =:id",nativeQuery=true)
  Product getProductByID(@Param("id") Long id);
}
