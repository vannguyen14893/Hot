package com.shopping.vn.service;

import com.shopping.vn.dto.OrderDto;
import com.shopping.vn.entity.Order;

public interface OrderService {
  Order createOrder(OrderDto OrderDto);
}
