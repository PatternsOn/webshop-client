package com.patternson.webshopclient.controllers;

import com.patternson.webshopclient.model.ArticleDTO;
import com.patternson.webshopclient.model.CartItem;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Controller
public class CartController {

    private RestTemplate restTemplate = new RestTemplate();

    List<CartItem> cartItems = new ArrayList<>();

    @GetMapping("/cart/{id}/addcartitem")
    public String addCartItem(@PathVariable String id) {

        ArticleDTO articleDTO = getArticleById(id);
        // Förmodligen problem här med då värdet går tillbaka
        CartItem cartItem = convertArticleToCartItem(articleDTO);


        // Lägger till qty på allt
        if (checkIfCartItemExist(cartItem)) {
            for (CartItem c : cartItems) {
                if (c.getArticleId() == cartItem.getArticleId()) {
                    System.out.println("Add quantity by one if the article all ready exist"); // Här blir det fel
                    c.setQuantity(c.getQuantity() + 1);
                }
            }
        } else {
            System.out.println("Add article to cart");
            cartItems.add(cartItem);
        }


        // Log på uppdateringarna
        for (CartItem c : cartItems) {
            System.out.println("Article name: " + c.getName() + " - - " + "qty: " + c.getQuantity());
        }

        return "redirect:/index/";
    }

    public int getCartItemQuantity() {
        int result = 0;

        for (CartItem c : cartItems) {
            result = result + c.getQuantity();
        }

        return result;
    }




    private Boolean checkIfCartItemExist(CartItem cartItem) {
        if (cartItems.isEmpty()) {
            System.out.println("Checking if the cart is empty");
            return false;
        }
        for (CartItem c : cartItems) {
            if (c.getArticleId() == cartItem.getArticleId()) {
                System.out.println("Checking if article exist in cart");
                return true;
            }
        }
        return false;
    }

    private ArticleDTO getArticleById(String id) {
        ArticleDTO articleDTO  = restTemplate.getForObject("http://localhost:8080/api/v1/articles/" + id, ArticleDTO.class);
        return articleDTO;
    }

    private CartItem convertArticleToCartItem(ArticleDTO articleDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setArticleId(articleDTO.getId());
        cartItem.setName(articleDTO.getName());
        cartItem.setPrice(articleDTO.getPrice());
        cartItem.setQuantity(1);

        return cartItem;
    }



    /*

    @PostMapping("article/{id}")
    public String updateArticle(@PathVariable String id, @ModelAttribute ArticleDTO articleDTO){
        restTemplate.put(BASE_URI + "/{id}", articleDTO, articleDTO.getId());


        System.out.println("Inside update: " + articleDTO.getName());

        return "redirect:/index/";
    }
     */
}
