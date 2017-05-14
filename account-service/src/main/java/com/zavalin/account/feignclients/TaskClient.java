package com.zavalin.account.feignclients;

import com.zavalin.account.model.Task;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "task-service", url = "http://localhost:8083")
public interface TaskClient {
    @RequestMapping(method = RequestMethod.POST, value = "/tasks")
    void createTask(Task task);

    @RequestMapping(method = RequestMethod.POST, value = "/tasks/current")
    List<Task> getTasks(String username);

    @RequestMapping(method = RequestMethod.GET, value = "/tasks/{taskId}")
    Task getTask(@PathVariable("taskId") long taskId);

    @RequestMapping(method = RequestMethod.PUT, value = "/tasks/{taskId}")
    void updateTask(@PathVariable("taskId") long taskId, Task task);

    @RequestMapping(method = RequestMethod.DELETE, value = "/tasks/{taskId}")
    void deleteTask(@PathVariable("taskId") long taskId);
}
