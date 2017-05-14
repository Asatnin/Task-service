package com.zavalin.notification.service;

import com.zavalin.notification.feignclients.AuthClient;
import com.zavalin.notification.feignclients.TaskClient;
import com.zavalin.notification.model.Notification;
import com.zavalin.notification.model.Task;
import com.zavalin.notification.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class SchedulerService {
    @Autowired
    private EmailService emailService;

    @Autowired
    private TaskClient taskClient;

    @Autowired
    private AuthClient authClient;

    @Autowired
    private NotificationRepository notificationRepository;

    private TaskScheduler scheduler;

    @Autowired
    public SchedulerService() {
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduler = new ConcurrentTaskScheduler(localExecutor);
    }

    @Async
    public void executeEmailSending(Notification note) {
        scheduler.schedule(() -> {
            if (notificationRepository.findOne(note.getId()) == null) {
                return;
            }

            Task task = taskClient.getTask(note.getTaskId());
            User user = authClient.getUser(task.getUsername());
            emailService.sendNotificationMessage(user.getEmail(),
                    task.getTitle(),
                    "Hello, " + user.getUsername() + "!\n\n"
                    + "You are planned to do " + task.getBody() + " at " + task.getDate() + ".\n\n"
                    + "Best regard, your notification service."
            );
        }, Date.from(note.getDate().atZone(ZoneId.systemDefault()).toInstant()));
    }
}
