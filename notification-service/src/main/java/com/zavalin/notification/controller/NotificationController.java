package com.zavalin.notification.controller;

import com.zavalin.notification.model.Notification;
import com.zavalin.notification.service.NotificationRepository;
import com.zavalin.notification.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class NotificationController {
    @Autowired
    private NotificationRepository noteRepository;

    @Autowired
    private SchedulerService schedulerService;

    @RequestMapping(path = "/notifications", method = RequestMethod.POST)
    @ResponseBody
    public void createNotification(@RequestBody Notification note) {
        note.setId(0);
        noteRepository.save(note);
        schedulerService.executeEmailSending(note);
    }

    //    @PreAuthorize("#oauth2.hasScope('internal')")
    @RequestMapping(path = "/notifications/current", method = RequestMethod.POST,
            consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public List<Notification> getNotifications(@RequestBody String taskId) {
        return noteRepository.findNotificationsByTaskId(Long.valueOf(taskId));
    }

    @RequestMapping(path = "/notifications/{notificationId}", method = RequestMethod.GET)
    @ResponseBody
    public Notification getNotification(@PathVariable long notificationId) {
        return noteRepository.findOne(notificationId);
    }

    @RequestMapping(path = "/notifications/{notificationId}", method = RequestMethod.PUT)
    @ResponseBody
    public void updateNotification(@PathVariable long notificationId, @RequestBody Notification note) {
        Notification oldNotification = noteRepository.findOne(notificationId);
        oldNotification.setDate(note.getDate());
        noteRepository.save(oldNotification);
    }

    @RequestMapping(path = "/notifications/{notificationId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteNotification(@PathVariable long notificationId) {
        Notification oldNotification = noteRepository.findOne(notificationId);
        noteRepository.delete(oldNotification);
    }
}
