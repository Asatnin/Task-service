package com.zavalin.auth.controller;

import com.zavalin.auth.model.User;
import com.zavalin.auth.service.UserRepository;
import com.zavalin.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/user/{username}")
    @ResponseBody
    public User getUser(@PathVariable("username") String username) {
        return userRepository.findUserByUsername(username);
    }

    @RequestMapping(value = "/users/current", method = RequestMethod.GET)
    @ResponseBody
    public Principal getUser(Principal principal) {
        return principal;
    }

    @PreAuthorize("#oauth2.hasScope('internal')")
    @RequestMapping(path = "/users", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(@RequestBody User user) {
        return userService.create(user).getUsername();
    }

    @PreAuthorize("#oauth2.hasScope('internal')")
    @RequestMapping(path = "/users/con", method = RequestMethod.POST,
            consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public User loadUser(@RequestBody String username) {
        return userRepository.findUserByUsername(username);
    }
}
