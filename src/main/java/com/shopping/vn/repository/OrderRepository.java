package com.shopping.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopping.vn.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
