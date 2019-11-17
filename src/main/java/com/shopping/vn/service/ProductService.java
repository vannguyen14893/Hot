package com.shopping.vn.service;

import java.math.BigDecimal;
import java.util.List;
import com.shopping.vn.dto.ProductDto;
import com.shopping.vn.dto.SortFilterDto;
import com.shopping.vn.entity.Product;

public interface ProductService {
  List<ProductDto> readAll(SortFilterDto filter);
  
  Product createProduct(ProductDto productDto);
  
  BigDecimal updatePriceSale(Long id,BigDecimal priceSale);
  
  ProductDto detailProductDto(Long id);
  
  Boolean deleteProduct(List<Long> ids);
  
  Product updateProduct(ProductDto productDto);
}
