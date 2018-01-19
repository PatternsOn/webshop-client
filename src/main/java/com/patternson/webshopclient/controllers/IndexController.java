package com.patternson.webshopclient.controllers;

import com.patternson.webshopclient.model.ArticleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Controller
public class IndexController {
    private static final String BASE_URI = "http://localhost:8080/api/v1/articles/";



//    @GetMapping("/")
//    String index(Principal principal) {
//        return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
//    }




    @RequestMapping({"", "/", "/index", "/home"})
    public String getIndexPage(Model model) {
        log.debug("Getting Index page");

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ArticleDTO[]> articleDTOResponseEntity = restTemplate.getForEntity(BASE_URI, ArticleDTO[].class);
        model.addAttribute("articles", articleDTOResponseEntity.getBody());

        return "index";

//        @RequestMapping("/admin")
//        public String getCustomers(Model model) {
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<Customer[]> customers = restTemplate.getForEntity("http://localhost:8080/api/customers", Customer[].class);
//            model.addAttribute("customers", customers.getBody());
//            return "admin";
//        }

    }

//    @Override
////    public ArticleDTO findById(Long l) {
////
////        RestTemplate restTemplate = new RestTemplate();
////        ArticleDTO articleDTO = restTemplate.getForObject(BASE_URI + l, ArticleDTO.class);
////
////        log.info(articleDTO.toString());
////
////
////        System.out.println("The object " + articleDTO);
////
////        return articleDTO;
////
////    }

}

