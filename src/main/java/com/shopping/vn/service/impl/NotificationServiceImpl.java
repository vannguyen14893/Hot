package com.shopping.vn.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shopping.vn.dto.NotificationDto;
import com.shopping.vn.entity.Comment;
import com.shopping.vn.entity.Notification;
import com.shopping.vn.entity.User;
import com.shopping.vn.repository.NotificationRepository;
import com.shopping.vn.repository.UserRepository;
import com.shopping.vn.service.NotificationService;
import com.shopping.vn.utils.Utils;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
  @Autowired
  private NotificationRepository notificationRepository;
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SimpMessagingTemplate template;

  @Override
  public Notification createNotificationComment(NotificationDto notificationDto, User receiver,Comment comment) {
    Notification notification = new Notification();
    notification.setStatus(false);
    User userSender = userRepository.findUserByEmail(Utils.getPrincipal());
    notification.setSender(userSender);
    notification.setReceiver(receiver);
    notification.setContent(notificationDto.getContent());
    notification.setComment(comment);
    notification.setCreatedDate(new Date());
    template.convertAndSend("/chat/" + receiver.getFullName(), notification);
    notificationRepository.save(notification);
    return notification;
  }

}
