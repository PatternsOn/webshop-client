package com.patternson.webshopclient.controllers;

import com.patternson.webshopclient.model.LoginCommand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String showLoginForm(Model model) {

        model.addAttribute("loginCommand", new LoginCommand());

        return "loginform";
    }

    @PostMapping("/dologin")
    public String doLogin(@Valid LoginCommand loginCommand, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "loginform";
        }

        return "redirect:index";
    }
}
