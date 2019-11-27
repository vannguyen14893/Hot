package com.shopping.vn.dto;

import lombok.Data;

@Data
public class CommentDto {
	private Long id;
	private String message;
	private ProductDto product;
	private UserDto createUser;
	private Long parentId;
	private Long productId;
	private String email;
	private Long countCommentChilds;
}
