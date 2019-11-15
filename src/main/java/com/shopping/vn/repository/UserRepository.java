package com.shopping.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopping.vn.entity.User;
import com.shopping.vn.repository.customer.UserRepositoryCustomer;

public interface UserRepository extends UserRepositoryCustomer, JpaRepository<User, Long> {
  User findUserByEmail(String email);

}
