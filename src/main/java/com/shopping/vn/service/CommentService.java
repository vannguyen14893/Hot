package com.shopping.vn.service;

import com.shopping.vn.dto.CommentDto;
import com.shopping.vn.entity.Comment;

public interface CommentService {
	Comment createCommentParent(CommentDto comment);

	Comment createCommentChild(CommentDto comment);
	
	boolean deleteCommentParent(Long id);
	
	boolean deleteCommentChild(Long id);
}
