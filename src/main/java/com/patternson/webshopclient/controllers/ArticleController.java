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
public class ArticleController {

    private static final String BASE_URI = "http://localhost:8080/api/v1/articles/";

//
//    @RequestMapping({"", "/", "/index"})
//    public String getIndexPage(Model model) {
//        log.debug("Getting article page");
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        ResponseEntity<ArticleDTO[]> articleDTOResponseEntity = restTemplate.getForEntity(BASE_URI, ArticleDTO[].class);
//        model.addAttribute("articles", articleDTOResponseEntity.getBody());
//
//        return "article";
//    }
}
