package com.shopping.vn.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shopping.vn.dto.ProductDto;
import com.shopping.vn.dto.SortFilterDto;
import com.shopping.vn.service.ProductService;
import com.shopping.vn.utils.ServiceStatus;

@RestController
@RequestMapping("/api/product")
public class ProductController {
  @Autowired
  private ProductService productService;

  @PostMapping(value = "/list-product")
  public ResponseEntity<?> readAll(@RequestBody SortFilterDto filter) {
    List<ProductDto> productDtos = productService.readAll(filter);
    if (productDtos.isEmpty()) {
      return new ResponseEntity<>(ServiceStatus.NO_DATA, HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(productDtos, HttpStatus.OK);
  }
}
