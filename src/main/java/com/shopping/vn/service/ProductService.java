package com.shopping.vn.service;

import java.util.List;
import com.shopping.vn.dto.ProductDto;
import com.shopping.vn.dto.SortFilterDto;

public interface ProductService {
  List<ProductDto> readAll(SortFilterDto filter);
}
