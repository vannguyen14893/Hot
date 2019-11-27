package com.shopping.vn.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.shopping.vn.dto.CommentDto;
import com.shopping.vn.dto.NotificationDto;
import com.shopping.vn.entity.Comment;
import com.shopping.vn.entity.Product;
import com.shopping.vn.entity.User;
import com.shopping.vn.exceptions.RuntimeExceptionHandling;
import com.shopping.vn.repository.CommentRepository;
import com.shopping.vn.repository.ProductRepository;
import com.shopping.vn.repository.UserRepository;
import com.shopping.vn.service.CommentService;
import com.shopping.vn.service.NotificationService;
import com.shopping.vn.utils.Constants;
import com.shopping.vn.utils.Utils;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private NotificationService notificationService;

  @Override
  public synchronized Comment createCommentParent(CommentDto commentDto) {
    Comment comment = new Comment();
    Product product = productRepository.findById(commentDto.getProductId())
        .orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.PRODUCT_NOT_FOUND));
    comment.setProduct(product);
    User user = userRepository.findUserByEmail(Utils.getPrincipal());
    comment.setCreateUser(user);
    comment.setMessage(commentDto.getMessage());
    comment.setParentId(0L);
    comment.setCreatedDate(new Date());
    commentRepository.save(comment);
    // send notification
    List<Comment> comments = commentRepository.findByProductAndParentId(product.getId());
    if (!CollectionUtils.isEmpty(comments)) {
      for (Comment comment2 : comments) {
        if (comment2.getCreateUser().getId() != user.getId()) {
          NotificationDto notificationDto = new NotificationDto();
          User userReceiver = userRepository.findUserByEmail(comment2.getCreateUser().getEmail());
          notificationDto.setContent("create comment");
          notificationService.createNotificationComment(notificationDto, userReceiver, comment);
        }

      }
    }
    return comment;
  }

  @Override
  public synchronized Comment createCommentChild(CommentDto commentDto) {
    Comment comment = commentRepository.findById(commentDto.getId())
        .orElseThrow(() -> new RuntimeExceptionHandling("Comment not found"));
    Product product = productRepository.findById(commentDto.getProductId())
        .orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.PRODUCT_NOT_FOUND));
    comment.setProduct(product);
    User user = userRepository.findUserByEmail(Utils.getPrincipal());
    comment.setCreateUser(user);
    comment.setMessage(commentDto.getMessage());
    comment.setParentId(commentDto.getId());
    comment.setCreatedDate(new Date());
    commentRepository.save(comment);

    // send notification
    NotificationDto notificationDto = new NotificationDto();
    User userReceiver = userRepository.findUserByEmail(comment.getCreateUser().getEmail());
    notificationDto.setContent("create comment");
    notificationService.createNotificationComment(notificationDto, userReceiver, comment);

    return comment;
  }

  @Override
  public boolean deleteCommentParent(Long id) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new RuntimeExceptionHandling("Comment not found"));
    User user = userRepository.findUserByEmail(Utils.getPrincipal());
    if (comment.getCreateUser().getId() == user.getId()) {
      List<Comment> comments = commentRepository.findByParentId(id);
      comments.add(comment);
      commentRepository.deleteAll(comments);
      return true;
    }
    return false;
  }

  @Override
  public boolean deleteCommentChild(Long id) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new RuntimeExceptionHandling("Comment not found"));
    User user = userRepository.findUserByEmail(Utils.getPrincipal());
    if (comment.getCreateUser().getId() == user.getId()) {
      commentRepository.delete(comment);
      return true;
    }

    return false;
  }

  @Override
  public Comment updateComment(CommentDto commentDto) {
    Comment comment = commentRepository.findById(commentDto.getId())
        .orElseThrow(() -> new RuntimeExceptionHandling("Comment not found"));
    comment.setId(commentDto.getId());
    comment.setMessage(commentDto.getMessage());
    comment.setParentId(commentDto.getParentId());
    Product product = productRepository.findById(commentDto.getProductId())
        .orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.PRODUCT_NOT_FOUND));
    comment.setProduct(product);
    User user = userRepository.findUserByEmail(Utils.getPrincipal());
    comment.setProduct(product);
    comment.setCreateUser(user);
    commentRepository.save(comment);
    return comment;
  }

  @Override
  public List<CommentDto> readCommentParent(Long productId) {
    List<Comment> comments = commentRepository.findByProductAndParentId(productId);
    List<CommentDto> commentDtos = new ArrayList<>();

    for (Comment comment : comments) {
      CommentDto commentDto = new CommentDto();
      commentDto.setId(comment.getId());
      commentDto.setMessage(comment.getMessage());
      commentDto.setParentId(comment.getParentId());
      User user = userRepository.findById(comment.getCreateUser().getId())
          .orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.USER_NOT_FOUND));
      commentDto.setEmail(user.getEmail());

      Long commentChilds = commentRepository.count(comment.getId());
      commentDto.setCountCommentChilds(commentChilds);
      commentDtos.add(commentDto);
    }
    return commentDtos;
  }

  @Override
  public List<CommentDto> readCommentChild(Long id) {
    List<Comment> comments = commentRepository.findByParentId(id);
    List<CommentDto> commentDtos = new ArrayList<>();
    for (Comment comment : comments) {
      CommentDto commentDto = new CommentDto();
      commentDto.setId(comment.getId());
      commentDto.setMessage(comment.getMessage());
      commentDto.setParentId(comment.getParentId());
      User user = userRepository.findById(comment.getCreateUser().getId())
          .orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.USER_NOT_FOUND));
      commentDto.setEmail(user.getEmail());
      commentDtos.add(commentDto);
    }
    return commentDtos;
  }

}
