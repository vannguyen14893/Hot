package com.shopping.vn.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shopping.vn.dto.ProductDto;
import com.shopping.vn.entity.CartItem;
import com.shopping.vn.entity.ShoppingCart;
import com.shopping.vn.entity.User;
import com.shopping.vn.repository.CartItemRepository;
import com.shopping.vn.repository.UserRepository;
import com.shopping.vn.service.CartItemService;
import com.shopping.vn.service.ShoppingCartService;
import com.shopping.vn.service.UserService;
import com.shopping.vn.utils.ServiceStatus;

@RestController
@RequestMapping(value = "/api/shopping-cart")
public class ShoppingCartController {
  @Autowired
  private CartItemService cartItemService;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ShoppingCartService shoppingCartService;
  @Autowired
  private CartItemRepository cartItemRepository;

  @PostMapping(value = "/add/{qty}")
  public ResponseEntity<ServiceStatus> addCartItem(@PathVariable("qty") Integer qty,
      @RequestBody ProductDto productDto) {
    cartItemService.addBookToCartItem(productDto, qty);
    return new ResponseEntity<>(ServiceStatus.ADD_SUCCESS, HttpStatus.OK);
  }

  @GetMapping("/getCartItemList")
  public List<CartItem> getCartItemList(Principal principal) {
    User user = userRepository.findUserByEmail(principal.getName());
    ShoppingCart shoppingCart = user.getShoppingCart();
    List<CartItem> cartItemList = cartItemRepository.findByShoppingCart(shoppingCart);
    shoppingCartService.updateShoppingCart(shoppingCart);
    return cartItemList;
  }

  @GetMapping("/getShoppingCart")
  public ShoppingCart getShoppingCart(Principal principal) {
    User user = userRepository.findUserByEmail(principal.getName());
    ShoppingCart shoppingCart = user.getShoppingCart();
    shoppingCartService.updateShoppingCart(shoppingCart);
    return shoppingCart;
  }

  @GetMapping("/removeItem")
  public ResponseEntity<?> removeItem(@RequestBody String id) {
    cartItemService.removeCartItem(cartItemService.findById(Long.parseLong(id)));
    return new ResponseEntity<>("Cart Item Removed Successfully!", HttpStatus.OK);
  }

  @RequestMapping("/updateCartItem")
  public ResponseEntity<?> updateCartItem(@RequestBody HashMap<String, String> mapper) {
    String cartItemId = mapper.get("cartItemId");
    String qty = mapper.get("qty");

    // CartItem cartItem = cartItemService.findById(Long.parseLong(cartItemId));
    // cartItem.setQty(Integer.parseInt(qty));

    // cartItemService.updateCartItem(cartItem);

    return new ResponseEntity<>("Cart Item Updated Successfully!", HttpStatus.OK);
  }
}
