package com.patternson.webshopclient.controllers;

import com.patternson.webshopclient.model.ArticleDTO;
import com.patternson.webshopclient.model.CartItem;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Controller
public class CartController {

    private RestTemplate restTemplate = new RestTemplate();

    List<CartItem> cartItems = new ArrayList<>();

    @GetMapping("/cart")
    public String getAllCartItems(Model model) {
        model.addAttribute("quantity", getCartItemQuantity());
        model.addAttribute("cartitems", cartItems);
        model.addAttribute("totalprice", getTotalPrice());

        return "cart/cart";
    }

    @GetMapping("/cart/{id}/addcartitem")
    public String addCartItem(@PathVariable Long id) {

        CartItem cartItem = getCartItemById(id);

        if (checkIfCartItemExist(cartItem)) {
            for (CartItem c : cartItems) {
                if (c.getArticleId() == cartItem.getArticleId()) {
                    System.out.println("Add quantity by one if the article all ready exist");
                    c.setQuantity(c.getQuantity() + 1);
                    c.setPrice(cartItem.getPrice().multiply(new BigDecimal(c.getQuantity())));
                }
            }
        } else {
            System.out.println("Add article to cart");
            cartItems.add(cartItem);
        }

        return "redirect:/index/";
    }

    @RequestMapping("cart/{id}/addquantity")
    public String addQuantityByOne(@PathVariable Long id) {

        CartItem cartItem = getCartItemById(id);

        System.out.println("Kolla om cartitem har värde: " + cartItem.getName());

        System.out.println("first check add");
        for (CartItem c: cartItems){
            if (c.getArticleId() == id) {
                System.out.println("Inne i add cartitem nr: " + id);
                c.setQuantity(c.getQuantity() + 1); // Lägg till metod för att räkna ut pris
                c.setPrice(cartItem.getPrice().multiply(new BigDecimal(c.getQuantity())));
            }
        }
        return "redirect:/cart";
    }

    @RequestMapping("cart/{id}/removequantity")
    public String removeQuantityByOne(@PathVariable Long id) {
        CartItem cartItem = getCartItemById(id);

        System.out.println("first check remove");
        for (CartItem c: cartItems) {
            if (c.getArticleId() == id) {
                System.out.println("Inne i remove cartitem nr: " + id);
                if (c.getQuantity() > 0) {
                    c.setQuantity(c.getQuantity() - 1); // Lägg till metod för att räkna ut pris
                    c.setPrice(cartItem.getPrice().multiply(new BigDecimal(c.getQuantity())));
                }
            }
        }
        return "redirect:/cart";
    }

    @RequestMapping("cart/{id}/delete")
    public String deleteCartItemById(@PathVariable Long id) {
        cartItems.removeIf(cartItem -> cartItem.getArticleId() == id);
        return "redirect:/cart";
    }

    /*
    @PostMapping("/article/{id}/delete")
    public String deleteArticleById(@PathVariable String id) {
        restTemplate.delete(BASE_URI + id, id);
        return "redirect:/index/";
    }
     */

    public int getCartItemQuantity() {
        int result = 0;

        for (CartItem c : cartItems) {
            result = result + c.getQuantity();
        }

        return result;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal result = new BigDecimal(0);

        for (CartItem c : cartItems) {
            result = result.add(c.getPrice());
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

    private CartItem getCartItemById(Long id) {

        String stringId = id.toString();
        ArticleDTO articleDTO = getArticleById(stringId);
        CartItem cartItem = convertArticleToCartItem(articleDTO);

        return cartItem;
    }

    private CartItem convertArticleToCartItem(ArticleDTO articleDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setArticleId(articleDTO.getId());
        cartItem.setName(articleDTO.getName());
        cartItem.setQuantity(1);
        cartItem.setPrice(articleDTO.getPrice());

        return cartItem;
    }



}
