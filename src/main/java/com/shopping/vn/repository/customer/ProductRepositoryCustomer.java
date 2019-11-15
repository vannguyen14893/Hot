package com.shopping.vn.repository.customer;

import java.util.List;
import com.shopping.vn.dto.SortFilterDto;
import com.shopping.vn.entity.Product;

public interface ProductRepositoryCustomer {
  List<Product> readAll(SortFilterDto filter);
}
