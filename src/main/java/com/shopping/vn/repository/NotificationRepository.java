package com.shopping.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopping.vn.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
