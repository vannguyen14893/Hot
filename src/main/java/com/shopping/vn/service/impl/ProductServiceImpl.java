package com.shopping.vn.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shopping.vn.dto.ProductDto;
import com.shopping.vn.dto.SortFilterDto;
import com.shopping.vn.entity.Product;
import com.shopping.vn.repository.ProductRepository;
import com.shopping.vn.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
  @Autowired
  private ProductRepository productRepository;

  @Override
  public List<ProductDto> readAll(SortFilterDto filter) {
    List<Product> products = productRepository.readAll(filter);
    List<ProductDto> productDtos = new ArrayList<>();
    for (Product product : products) {
      productDtos.add(ProductDto.convert(product));
    }


    return productDtos;
  }

}
