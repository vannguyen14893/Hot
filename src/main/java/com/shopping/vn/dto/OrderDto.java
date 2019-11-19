package com.shopping.vn.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class OrderDto {

  private Long id;
  private Date orderDate;
  private Date shippingDate;
  private String shippingMethod;
  private String orderStatus;
  private BigDecimal orderTotal;
  private List<CartItemDto> cartItemList;
  private UserDto user;
}
