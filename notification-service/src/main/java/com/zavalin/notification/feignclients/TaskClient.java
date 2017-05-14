package com.zavalin.notification.feignclients;

import com.zavalin.notification.model.Task;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "task-service", url = "http://localhost:8083")
public interface TaskClient {
    @RequestMapping(method = RequestMethod.GET, value = "/tasks/{taskId}")
    Task getTask(@PathVariable("taskId") long taskId);
}
