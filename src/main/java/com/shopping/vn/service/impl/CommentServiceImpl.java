package com.shopping.vn.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.shopping.vn.dto.CommentDto;
import com.shopping.vn.entity.Comment;
import com.shopping.vn.entity.Product;
import com.shopping.vn.entity.User;
import com.shopping.vn.exceptions.RuntimeExceptionHandling;
import com.shopping.vn.repository.CommentRepository;
import com.shopping.vn.repository.ProductRepository;
import com.shopping.vn.repository.UserRepository;
import com.shopping.vn.service.CommentService;
import com.shopping.vn.utils.Constants;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Comment createCommentParent(CommentDto commentDto) {
		Comment comment = new Comment();
		Product product = productRepository.findById(commentDto.getProductId())
				.orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.PRODUCT_NOT_FOUND));
		comment.setProduct(product);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findUserByEmail(principal.toString());
		comment.setCreateUser(user);
		comment.setMessage(commentDto.getMessage());
		comment.setParentId(0L);
		commentRepository.save(comment);
		return comment;
	}

	@Override
	public Comment createCommentChild(CommentDto commentDto) {
		Comment comment = commentRepository.findById(commentDto.getId())
				.orElseThrow(() -> new RuntimeExceptionHandling("Comment not found"));
		Product product = productRepository.findById(commentDto.getProductId())
				.orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.PRODUCT_NOT_FOUND));
		comment.setProduct(product);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findUserByEmail(principal.toString());
		comment.setCreateUser(user);
		comment.setMessage(commentDto.getMessage());
		comment.setParentId(commentDto.getId());
		commentRepository.save(comment);
		return comment;
	}

	@Override
	public boolean deleteCommentParent(Long id) {
		
		return false;
	}

	@Override
	public boolean deleteCommentChild(Long id) {
		
		return false;
	}

}
