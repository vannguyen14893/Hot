package com.shopping.vn.service.impl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shopping.vn.dto.CartItemDto;
import com.shopping.vn.dto.ProductDto;
import com.shopping.vn.entity.BookToCartItem;
import com.shopping.vn.entity.CartItem;
import com.shopping.vn.entity.Product;
import com.shopping.vn.entity.ShoppingCart;
import com.shopping.vn.entity.User;
import com.shopping.vn.exceptions.RuntimeExceptionHandling;
import com.shopping.vn.repository.BookToCartItemRepository;
import com.shopping.vn.repository.CartItemRepository;
import com.shopping.vn.repository.ProductRepository;
import com.shopping.vn.repository.UserRepository;
import com.shopping.vn.service.CartItemService;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {
  @Autowired
  private CartItemRepository cartItemRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private BookToCartItemRepository bookToCartItemRepository;
  @Autowired
  ModelMapper mapper;

  @Override
  public CartItem addBookToCartItem(ProductDto productDto, int qty) {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = userRepository.findUserByEmail(principal.toString());
    List<CartItem> cartItems = findByShoppingCart(user.getShoppingCart());

    for (CartItem cartItem : cartItems) {
      if (productDto.getId() == cartItem.getProduct().getId()) {
        cartItem.setQty(cartItem.getQty() + qty);

        cartItem.setSubtotal(calculateCost(5, productDto.getPrice()));
        cartItemRepository.save(cartItem);
        return cartItem;
      }
    }

    CartItem cartItem = new CartItem();
    cartItem.setShoppingCart(user.getShoppingCart());
    Product product = productRepository.findById(productDto.getId()).orElse(null);
    cartItem.setProduct(product);;
    cartItem.setQty(qty);

    cartItem.setSubtotal(calculateCost(5, productDto.getPrice()));

    cartItem = cartItemRepository.save(cartItem);

    BookToCartItem bookToCartItem = new BookToCartItem();
    bookToCartItem.setProduct(product);
    bookToCartItem.setCartItem(cartItem);
    bookToCartItemRepository.save(bookToCartItem);

    return cartItem;
  }



  public BigDecimal calculateCost(int itemQuantity, BigDecimal itemPrice) {
    BigDecimal itemCost = itemPrice.multiply(new BigDecimal(itemQuantity));
    System.out.println(itemCost);
    return itemCost;
  }

  public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
    return cartItemRepository.findByShoppingCart(shoppingCart);
  }

  @Transactional
  @Override
  public Boolean removeCartItem(CartItemDto cartItemDto) {
    CartItem cartItem = cartItemRepository.findById(cartItemDto.getId())
        .orElseThrow(() -> new RuntimeExceptionHandling("CartItem not found"));
    bookToCartItemRepository.deleteByCartItem(cartItem);
    cartItemRepository.delete(cartItem);
    return true;
  }


  @Override
  public CartItem updateCartItem(CartItemDto cartItemDto) {
    BigDecimal bigDecimal =
        cartItemDto.getProduct().getPrice().multiply(new BigDecimal(cartItemDto.getQty()));
    bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);

    CartItem cartItem = cartItemRepository.findById(cartItemDto.getId())
        .orElseThrow(() -> new RuntimeExceptionHandling("CartItem not found"));
    cartItem.setSubtotal(bigDecimal);
    cartItemRepository.save(cartItem);
    return cartItem;

  }

  @Override
  public CartItemDto findById(Long id) {
    CartItem cartItem = cartItemRepository.findById(id)
        .orElseThrow(() -> new RuntimeExceptionHandling("CartItem not found"));
    CartItemDto cartItemDto = new CartItemDto();
    cartItemDto = mapper.map(cartItem, CartItemDto.class);
    return cartItemDto;
  }

  @Override
  public CartItem save(CartItemDto cartItemDto) {
    CartItem cartItem = new CartItem();
    cartItem = mapper.map(cartItemDto, CartItem.class);
    return cartItemRepository.save(cartItem);
  }
}
