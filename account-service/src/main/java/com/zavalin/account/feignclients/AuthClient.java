package com.zavalin.account.feignclients;

import com.zavalin.account.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "auth-service", url = "http://localhost:5000")
public interface AuthClient {
    @RequestMapping(method = RequestMethod.POST, value = "/uaa/users")
    String createUser(User user);

    @RequestMapping(method = RequestMethod.POST, value = "/uaa/users/con")
    User loadUser(String username);
}
