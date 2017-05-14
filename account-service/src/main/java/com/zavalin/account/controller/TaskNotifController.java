package com.zavalin.account.controller;

import com.zavalin.account.feignclients.NotificationClient;
import com.zavalin.account.feignclients.TaskClient;
import com.zavalin.account.model.ConnectionException;
import com.zavalin.account.model.NewNote;
import com.zavalin.account.model.Notification;
import com.zavalin.account.model.Task;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TaskNotifController {
    @Autowired
    private TaskClient taskClient;

    @Autowired
    private NotificationClient notificationClient;

    @RequestMapping(path = "/task", method = RequestMethod.GET)
    public ModelAndView showNewTaskForm() {
        ModelAndView modelAndView = new ModelAndView("new-task");
        modelAndView.addObject("task", new Task());
        return modelAndView;
    }

    @RequestMapping(path = "/task", method = RequestMethod.POST)
    public String addNewTask(Task task, Principal principal) {
        if (task.getDate().isBefore(LocalDateTime.now())) {
            return "redirect:home";
        }

        task.setUsername(principal.getName());
        taskClient.createTask(task);
        return "redirect:/home";
    }

    @RequestMapping(path = "/task/{taskId}", method = RequestMethod.GET)
    public ModelAndView editTask(@PathVariable long taskId) {
        Task task = taskClient.getTask(taskId);
        if (task == null) {
            ModelAndView modelAndView = new ModelAndView("/home");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("edit-task");
        modelAndView.addObject("task", task);
        return modelAndView;
    }

    @RequestMapping(path = "/task/{taskId}", method = RequestMethod.POST)
    public String updateTask(Task task, @PathVariable long taskId) {
        if (task.getDate().isBefore(LocalDateTime.now())) {
            return "redirect:home";
        }

        taskClient.updateTask(taskId, task);
        return "redirect:/home";
    }

    @RequestMapping(path = "/task/{taskId}/del", method = RequestMethod.GET)
    public String deleteTask(@PathVariable long taskId) {
        taskClient.deleteTask(taskId);
        return "redirect:/home";
    }

    @RequestMapping(path = "/task/{taskId}/notifications", method = RequestMethod.GET)
    public ModelAndView getNotificationsForTask(@PathVariable long taskId) {
        List<Notification> notes = notificationClient.getNotifications(Long.valueOf(taskId).toString());
        ModelAndView modelAndView = new ModelAndView("notes");
        modelAndView.addObject("notes", notes);
        modelAndView.addObject("newnote", new NewNote());
        return modelAndView;
    }

    @RequestMapping(path = "/task/{taskId}/notifications", method = RequestMethod.POST)
    public String addNotificationsForTask(@PathVariable long taskId, NewNote newNote) {
        Task task = taskClient.getTask(taskId);
        if (newNote.getDate().isAfter(task.getDate()) || newNote.getDate().isBefore(LocalDateTime.now())) {
            return "redirect:/task/" + taskId + "/notifications";
        }

        Notification note = new Notification();
        note.setTaskId(taskId);
        note.setDate(newNote.getDate());
        notificationClient.createNotification(note);
        return "redirect:/task/" + taskId + "/notifications";
    }

    @RequestMapping(path = "/task/{taskId}/notifications/{notificationId}/del", method = RequestMethod.GET)
    public String deleteNote(@PathVariable long taskId, @PathVariable long notificationId) {
        notificationClient.deleteNotification(notificationId);
        return "redirect:/task/" + taskId + "/notifications";
    }

    @ExceptionHandler({ ConnectionException.class, FeignException.class })
    public String handleError(Exception e) {
        System.out.println(e.getMessage());
        return "redirect:/home";
    }
}
