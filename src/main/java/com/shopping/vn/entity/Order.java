package com.shopping.vn.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@Entity
@Table(name = "user_order")
public class Order implements Serializable {

  private static final long serialVersionUID = 2893475845L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Date orderDate;
  private Date shippingDate;
  private String shippingMethod;
  private String orderStatus;
  private BigDecimal orderTotal;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<CartItem> cartItemList;

  // @OneToOne(cascade=CascadeType.ALL)
  // private ShippingAddress shippingAddress;
  //
  // @OneToOne(cascade=CascadeType.ALL)
  // private BillingAddress billingAddress;
  //
  // @OneToOne(cascade=CascadeType.ALL)
  // private Payment payment;

  @ManyToOne
  @JsonIgnore
  private User user;

}
