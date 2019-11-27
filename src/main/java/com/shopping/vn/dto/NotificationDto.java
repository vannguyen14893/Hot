package com.shopping.vn.dto;

import java.util.Date;
import lombok.Data;

@Data
public class NotificationDto {
  private Long id;
  private String content;
  private Boolean status;
  private Date createdDate;
  private UserDto receiver;
  private UserDto sender;
  private CommentDto comment;
  private String emailReceiver;

}
