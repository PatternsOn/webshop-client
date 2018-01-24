package com.patternson.webshopclient.controllers;

import com.patternson.webshopclient.exceptions.NotFoundException;
import com.patternson.webshopclient.model.ArticleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;



@Slf4j
@Controller
public class ArticleController {

    private static final String BASE_URI = "http://localhost:8080/api/v1/articles/";


    // Visar en produkts parametrar i ett formulär
    @GetMapping("/article/{id}/articleupdateform")
    public String showArticleFormById(@PathVariable String id, Model model){
        RestTemplate restTemplate = new RestTemplate();
        ArticleDTO articleDTO = restTemplate.getForObject(BASE_URI + id, ArticleDTO.class);

        model.addAttribute("article", articleDTO);

        return "article/articleupdateform";
    }

    @GetMapping("article/new")
    public String newArticle(Model model){
        model.addAttribute("article", new ArticleDTO());

        return "articlupdateform";
    }



    @PostMapping("article/{id}")
    public String updateArticle(@PathVariable String id, @ModelAttribute ArticleDTO articleDTO){

        System.out.println("Inne i update article " + articleDTO.getDescription());
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.put(BASE_URI + "/{id}", articleDTO, articleDTO.getId());
        return "redirect:/index/";
    }

    @PostMapping("/createarticle")
    public String createArticle(@ModelAttribute ArticleDTO articleDTO) {


//        resttemplate here


        return "redirect:/index/";
    }

//    @RequestMapping(value = "/article/{id}", method = RequestMethod.PATCH)
//    public String addArticle(@PathVariable String id, @RequestBody ArticleDTO articleDTO) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        ArticleDTO articleDTO = restTemplate.patchForObject(BASE_URI + id, ArticleDTO.class);
//
//        return "index";
//    }

//    @PatchMapping("/{id}/addarticle/")
//    public String addArticle(@PathVariable String id, ArticleDTO articleDTO) {
//        System.out.println("Inne i patchmapping");
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpEntity<ArticleDTO> requestBody = new HttpEntity<>(articleDTO, headers);
//
//        restTemplate.put(BASE_URI + id, requestBody, new ArticleDTO[] {});
//
//        return "index";
//
//    }



    /*

    HttpHeaders headers = new HttpHeaders();
MediaType mediaType = new MediaType("application", "merge-patch+json");
headers.setContentType(mediaType);

HttpEntity<yourObejct> entity = new HttpEntity<>(yourObejct, headers);
HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
RestTemplate restTemplate = new RestTemplate(requestFactory);

restTemplate.exchange(uri, HttpMethod.PATCH, entity, Void.class);

    -----------------------------

    @RequestMapping(value = "/", method = RequestMethod.POST)
public ResponseEntity<Car> update(@RequestBody Car car) {

    if (car != null) {
        car.setMiles(car.getMiles() + 100);
    }

    // TODO: call persistence layer to update
    return new ResponseEntity<Car>(car, HttpStatus.OK);
}

    @RequestMapping(value = "/", method = RequestMethod.POST)
public ResponseEntity<Car> update(@RequestBody Car car) {
    ...
}

    RestTemplate restTemplate = new RestTemplate();

HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
requestFactory.setConnectTimeout(TIMEOUT);
requestFactory.setReadTimeout(TIMEOUT);

restTemplate.setRequestFactory(requestFactory);
     */

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


//    @RequestMapping(value = "/addarticle", method = RequestMethod.POST)
//    public String addArticle(@Valid ArticleDTO articleDTO, BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors()) {
//            return "articleform";
//        }
//
//        return "redirect:index";
//    }
}
