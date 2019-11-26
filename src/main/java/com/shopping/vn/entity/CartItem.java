package com.shopping.vn.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Data
public class CartItem implements Serializable {

  private static final long serialVersionUID = -189412481L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private int qty;
  private BigDecimal subtotal;

  @OneToOne
  private Product product;

  @OneToMany(mappedBy = "cartItem")
  @JsonIgnore
  private List<BookToCartItem> bookToCartItemList;

  @ManyToOne
  @JoinColumn(name = "shopping_cart_id")
  private ShoppingCart shoppingCart;

  @ManyToOne
  @JoinColumn(name = "order_id")
  @JsonIgnore
  private Order order;

}
