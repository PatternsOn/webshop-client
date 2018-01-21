package com.patternson.webshopclient.controllers;

import com.patternson.webshopclient.model.ArticleDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class ArticleController {

    @RequestMapping("/addarticle")
    public String articleForm(Model model) {

        model.addAttribute("articleDTO", new ArticleDTO());

        return "articleform";
    }

    @RequestMapping(value = "/doaddarticle", method = RequestMethod.POST)
    public String doAddArticle(@Valid ArticleDTO articleDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "articleform";
        }

        return "redirect:index";
    }
}
