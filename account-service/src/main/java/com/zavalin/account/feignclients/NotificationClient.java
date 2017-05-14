package com.zavalin.account.feignclients;

import com.zavalin.account.model.Notification;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "notification-service", url = "http://localhost:8084")
public interface NotificationClient {
    @RequestMapping(method = RequestMethod.POST, value = "/notifications")
    void createNotification(Notification notification);

    @RequestMapping(method = RequestMethod.POST, value = "/notifications/current")
    List<Notification> getNotifications(String taskId);

    @RequestMapping(method = RequestMethod.GET, value = "/notifications/{notificationId}")
    Notification getNotification(@PathVariable("notificationId") long notificationId);

    @RequestMapping(method = RequestMethod.PUT, value = "/notifications/{notificationId}")
    void updateNotification(@PathVariable("notificationId") long noteId, Notification note);

    @RequestMapping(method = RequestMethod.DELETE, value = "/notifications/{notificationId}")
    void deleteNotification(@PathVariable("notificationId") long noteId);
}
