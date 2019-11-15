package com.shopping.vn.repository.customer;

import java.util.List;
import com.shopping.vn.dto.SortFilterDto;
import com.shopping.vn.entity.User;

public interface UserRepositoryCustomer {
  List<User> readAll(SortFilterDto filter);
}
