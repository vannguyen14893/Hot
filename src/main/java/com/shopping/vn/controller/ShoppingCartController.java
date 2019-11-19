package com.shopping.vn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shopping.vn.dto.ProductDto;
import com.shopping.vn.service.CartItemService;
import com.shopping.vn.utils.ServiceStatus;

@RestController
@RequestMapping(value = "/api/shopping-cart")
public class ShoppingCartController {
  @Autowired
  private CartItemService cartItemService;

  @PostMapping(value="/add/{qty}")
  public ResponseEntity<?> addCartItem(@PathVariable("qty") Integer qty,@RequestBody ProductDto productDto) {
    cartItemService.addBookToCartItem(productDto, qty);
    return new ResponseEntity<>(ServiceStatus.ADD_SUCCESS,HttpStatus.OK);
  }
}
