package com.zavalin.account.controller;

import com.zavalin.account.feignclients.TaskClient;
import com.zavalin.account.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path = "/home")
public class HomeContoller {
    @Autowired
    private TaskClient taskClient;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showHome(Principal principal) {
        String username = principal.getName();
        List<Task> tasks;
        try {
            tasks = taskClient.getTasks(username);
        } catch (Exception e) {
            tasks = new ArrayList<>();
        }
//        List<Task> tasks = new ArrayList<>();
//        Task t1 = new Task(); t1.setId(123); t1.setTitle("Task1"); t1.setBody("I need to do task1"); t1.setDate(LocalDateTime.now());
//        Task t2 = new Task(); t2.setId(456); t2.setTitle("Task2"); t2.setBody("I need to do task2"); t2.setDate(LocalDateTime.now());
//        List<Task> tasks = Arrays.asList(t1, t2);

        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("tasks", tasks);
        return modelAndView;
    }
}
