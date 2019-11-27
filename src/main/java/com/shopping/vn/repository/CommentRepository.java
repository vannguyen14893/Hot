package com.shopping.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.vn.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}
