package com.patternson.webshopclient.controllers;

import com.patternson.webshopclient.model.ArticleDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@Controller
public class ArticleController {

    private static final String BASE_URI = "http://localhost:8080/api/v1/articles/";


    @RequestMapping("/articleform")
    public String getArticleById() {
        return "articleform";
    }



    @GetMapping("/article/{id}/articleform")
    public String showById(@PathVariable String id, Model model){
        RestTemplate restTemplate = new RestTemplate();
        ArticleDTO articleDTO = restTemplate.getForObject(BASE_URI + id, ArticleDTO.class);

        model.addAttribute("article", articleDTO);

        return "article/articleform";
    }



//    @PostMapping("recipe")
//    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult){
//
//        if(bindingResult.hasErrors()){
//
//            bindingResult.getAllErrors().forEach(objectError -> {
//                log.debug(objectError.toString());
//            });
//
//            return RECIPE_RECIPEFORM_URL;
//        }
//
//        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
//
//        return "redirect:/recipe/" + savedCommand.getId() + "/show";
//    }


    @RequestMapping(value = "/addarticle", method = RequestMethod.POST)
    public String addArticle(@Valid ArticleDTO articleDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "articleform";
        }

        return "redirect:index";
    }
}
