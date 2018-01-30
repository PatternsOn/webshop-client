package com.patternson.webshopclient.controllers;

import com.patternson.webshopclient.exceptions.NotFoundException;
import com.patternson.webshopclient.model.ArticleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Slf4j
@Controller
public class ArticleController {

    private static final String BASE_URI = "http://localhost:8080/api/v1/articles/";
    private RestTemplate restTemplate = new RestTemplate();

    // Visar en produkts parametrar i ett formulär
    @GetMapping("/article/{id}/articleupdateform")
    public String showArticleInUpdateFormById(@PathVariable String id, Model model){
        ArticleDTO articleDTO = restTemplate.getForObject(BASE_URI + id, ArticleDTO.class);
        model.addAttribute("article", articleDTO);

        return "article/articleupdateform";
    }

    // Visar tomt formulär
    @GetMapping("/article/articlecreateform")
    public String showArticleCreateForm(Model model){
        model.addAttribute("article", new ArticleDTO());

        return "article/articlecreateform";
    }

    @PostMapping("article/{id}")
    public String updateArticle(@Valid @ModelAttribute("article") ArticleDTO articleDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){

            System.out.println("Inne i error check");
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "article/articleupdateform";
        }

        restTemplate.put(BASE_URI + "/{id}", articleDTO, articleDTO.getId());

        return "redirect:/index/";
    }

    @PostMapping("/createarticle")
    public String createArticle(@Valid @ModelAttribute("article") ArticleDTO articleDTO, BindingResult bindingResult) { // Testar Bindingresukt och validation

        if(bindingResult.hasErrors()){

            System.out.println("Inne i error check");
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "article/articlecreateform";
        }


        restTemplate.postForObject(BASE_URI, articleDTO, ArticleDTO.class);

        return "redirect:/index/";
    }

    @GetMapping("/article/{id}/confirmdelete")  // Stoppa in id istället
    public String confirmDeleteArticleById(@ModelAttribute ArticleDTO articleDTO, Model model) {
        model.addAttribute("article", articleDTO);

        return "article/confirmdelete";
    }

    @GetMapping("/article/canceldelete")
    public String cancelDelete() {

        return "redirect:/index/";
    }

    @PostMapping("/article/{id}/delete")
    public String deleteArticleById(@PathVariable String id) {
        restTemplate.delete(BASE_URI + id, id);
        return "redirect:/index/";
    }


    // Används inte i nuläget pga att alla requests sker med ett id som existerar
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){

        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
