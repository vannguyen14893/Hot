package com.shopping.vn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.shopping.vn.dto.OrderDto;
import com.shopping.vn.service.OrderService;
import com.shopping.vn.utils.ServiceStatus;

@RestController
public class OrderController {
  @Autowired
  private OrderService orderService;

  @PostMapping(value = "/checkout")
  public ResponseEntity<?> checkout(@RequestBody OrderDto orderDto) {
    orderService.createOrder(orderDto);
    return new ResponseEntity<>(ServiceStatus.ADD_SUCCESS,HttpStatus.OK);
  }
}
