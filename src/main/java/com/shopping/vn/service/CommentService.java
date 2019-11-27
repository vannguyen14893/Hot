package com.shopping.vn.service;

import java.util.List;
import com.shopping.vn.dto.CommentDto;
import com.shopping.vn.entity.Comment;

public interface CommentService {
	Comment createCommentParent(CommentDto comment);

	Comment createCommentChild(CommentDto comment);
	
	boolean deleteCommentParent(Long id);
	
	boolean deleteCommentChild(Long id);
	
	Comment updateComment(CommentDto comment);
	
	List<CommentDto> readCommentParent(Long productId);
	
	List<CommentDto> readCommentChild(Long id);
}
