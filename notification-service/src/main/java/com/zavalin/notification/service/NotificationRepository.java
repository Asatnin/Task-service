package com.zavalin.notification.service;

import com.zavalin.notification.model.Notification;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface NotificationRepository extends CrudRepository<Notification, Long> {
    List<Notification> findNotificationsByTaskId(long taskId);
}
