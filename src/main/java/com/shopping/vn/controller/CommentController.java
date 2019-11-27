package com.shopping.vn.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shopping.vn.dto.CommentDto;
import com.shopping.vn.service.CommentService;
import com.shopping.vn.utils.ServiceStatus;

@RestController
@RequestMapping(value = "/api/comment")
public class CommentController {
  @Autowired
  private CommentService commentService;

  @PostMapping(value = "/add-comment-parent")
  public ResponseEntity<?> createCommentParent(@RequestBody CommentDto comment) {
    try {
      commentService.createCommentParent(comment);
      return new ResponseEntity<>(ServiceStatus.ADD_SUCCESS, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(ServiceStatus.ADD_SUCCESS, HttpStatus.OK);
    }
   
  }

  @PostMapping(value = "/add-comment-child")
  public ResponseEntity<?> createCommentChild(@RequestBody CommentDto comment) {
    commentService.createCommentChild(comment);
    return new ResponseEntity<>(ServiceStatus.ADD_SUCCESS, HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete-comment-parent/{id}")
  public ResponseEntity<?> deleteCommentParent(@PathVariable Long id) {
    commentService.deleteCommentParent(id);
    return new ResponseEntity<>(ServiceStatus.DELETE_SUCCESS, HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete-comment-child/{id}")
  public ResponseEntity<?> deleteCommentChild(@PathVariable Long id) {
    commentService.deleteCommentChild(id);
    return new ResponseEntity<>(ServiceStatus.DELETE_SUCCESS, HttpStatus.OK);
  }

  @PutMapping(value = "/update-comment")
  public ResponseEntity<?> updateComment(@RequestBody CommentDto comment) {
    commentService.updateComment(comment);
    return new ResponseEntity<>(ServiceStatus.UPDATE_SUCCESS, HttpStatus.OK);
  }

  @PostMapping(value = "/read-comment-parent/{productId}")
  public ResponseEntity<?> readCommentParent(@PathVariable Long productId) {
    List<CommentDto> readCommentParent = commentService.readCommentParent(productId);
    return new ResponseEntity<>(readCommentParent, HttpStatus.OK);
  }

  @PostMapping(value = "/read-comment-child/{id}")
  public ResponseEntity<?> readCommentChild(@PathVariable Long id) {
    List<CommentDto> readCommentChild = commentService.readCommentChild(id);
    return new ResponseEntity<>(readCommentChild, HttpStatus.OK);
  }
}
