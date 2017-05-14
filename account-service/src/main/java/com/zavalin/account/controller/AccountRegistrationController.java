package com.zavalin.account.controller;

import com.zavalin.account.feignclients.AuthClient;
import com.zavalin.account.model.User;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;

@Controller
public class AccountRegistrationController {
    @Autowired
    private AuthClient authClient;

    @RequestMapping(path = "/registration", method = RequestMethod.GET)
    public String showRegForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    public String registerAccount(@Valid @ModelAttribute User user) {
        String username = authClient.createUser(user);
        return "redirect:/login";
    }

    @ExceptionHandler(FeignException.class)
    public String handleError() {
        return "redirect:/registration";
    }
}
