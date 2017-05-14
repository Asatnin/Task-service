package com.zavalin.task.controller;

import com.zavalin.task.model.Task;
import com.zavalin.task.service.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping(path = "/tasks", method = RequestMethod.POST)
    @ResponseBody
    public void createTask(@RequestBody Task task) {
        task.setId(0);
        taskRepository.save(task);
    }

//    @PreAuthorize("#oauth2.hasScope('internal')")
    @RequestMapping(path = "/tasks/current", method = RequestMethod.POST,
            consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public List<Task> getTasks(@RequestBody String username) {
        return taskRepository.findTasksByUsername(username);
    }

    @RequestMapping(path = "/tasks/{taskId}", method = RequestMethod.GET)
    @ResponseBody
    public Task getTask(@PathVariable long taskId) {
        return taskRepository.findOne(taskId);
    }

    @RequestMapping(path = "/tasks/{taskId}", method = RequestMethod.PUT)
    @ResponseBody
    public void updateTask(@PathVariable long taskId, @RequestBody Task task) {
        Task oldTask = taskRepository.findOne(taskId);
        oldTask.setTitle(task.getTitle());
        oldTask.setBody(task.getBody());
        oldTask.setDate(task.getDate());
        taskRepository.save(oldTask);
    }

    @RequestMapping(path = "/tasks/{taskId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteTask(@PathVariable long taskId) {
        Task oldTask = taskRepository.findOne(taskId);
        taskRepository.delete(oldTask);
    }
}
