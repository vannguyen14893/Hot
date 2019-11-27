package com.shopping.vn.service;

import com.shopping.vn.dto.NotificationDto;
import com.shopping.vn.entity.Comment;
import com.shopping.vn.entity.Notification;
import com.shopping.vn.entity.User;

public interface NotificationService {
  Notification createNotificationComment(NotificationDto notificationDto,User receiver,Comment comment);
}
