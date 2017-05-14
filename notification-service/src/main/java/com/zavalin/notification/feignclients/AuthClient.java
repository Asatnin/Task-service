package com.zavalin.notification.feignclients;

import com.zavalin.notification.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "auth-service", url = "http://localhost:5000")
public interface AuthClient {
    @RequestMapping(method = RequestMethod.GET, value = "/uaa/user/{username}")
    User getUser(@PathVariable("username") String username);
}
