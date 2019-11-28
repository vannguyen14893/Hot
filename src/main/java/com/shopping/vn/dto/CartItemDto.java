package com.shopping.vn.dto;

import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.shopping.vn.entity.CartItem;

import lombok.Data;

@Data
public class CartItemDto {
	private Long id;
	private int qty;
	private BigDecimal subtotal;
	private ProductDto product;
	private List<BookToCartItemDto> bookToCartItemList;
	private ShoppingCartDto shoppingCart;
	private OrderDto order;

	public static final CartItemDto cartItem(CartItem cartItem) {
		ModelMapper model = new ModelMapper();
		CartItemDto cartItemDto = new CartItemDto();
		cartItemDto = model.map(cartItem, CartItemDto.class);
		return cartItemDto;
	}
}
