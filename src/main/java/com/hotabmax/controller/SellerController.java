package com.hotabmax.controller;

import com.hotabmax.filters.FilterAutentificationSellerPage;
import com.hotabmax.controller.keygenerator.ClassOfKey;
import com.hotabmax.models.*;
import com.hotabmax.servicesJPA.HistoryOfSellingService;
import com.hotabmax.servicesJPA.ProductService;
import com.hotabmax.servicesJPA.SortService;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SellerController {
    private Gson gson = new Gson();
    private Key key;
    private List<User> users = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Sort> sorts = new ArrayList<>();
    private List<HistoryOfSelling> historyOfSelling = new ArrayList<>();

    @Autowired
    private FilterAutentificationSellerPage filterAutentificationSellerPage;
    @Autowired
    private ProductService productService;
    @Autowired
    private SortService sortService;
    @Autowired
    private HistoryOfSellingService historyOfSellingService;
    @Autowired
    private ClassOfKey classOfKey;

    @Bean
    private void getKeySaller() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        key = classOfKey.getKey();
    }

    @GetMapping("/seller")
    public String getPage(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        return filterAutentificationSellerPage.autentification(cookies, key);
    }

    @PostMapping("/seller/getTableSorts")
    @ResponseBody
    public String getAdminTablesSorts(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentificationSellerPage.autentification(cookies, key).equals("seller")){
            sorts = sortService.findAll();
        }
        return gson.toJson(sorts);
    }

    @PostMapping("/seller/getTableProducts")
    @ResponseBody
    public String getTableProducts(HttpServletRequest httpServletRequest,
                                               @RequestParam("name") String name) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentificationSellerPage.autentification(cookies, key).equals("seller")){
            products = productService.findByName(name);
        }
        return gson.toJson(products);
    }

    @PostMapping("/seller/getTableProductsBySort")
    @ResponseBody
    public String getTableProductsBySort(HttpServletRequest httpServletRequest,
                                               @RequestParam("nameSort") String nameSort) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentificationSellerPage.autentification(cookies, key).equals("seller")){
            sorts = sortService.findByName(nameSort);
            int sortid = (int) sorts.get(0).getId();
            products = productService.findBySortId(sortid);
        }
        return gson.toJson(products);
    }

    @PostMapping("/seller/tranzactionDeleteProductAmount")
    @ResponseBody
    public String tranzactionDeleteAmount(HttpServletRequest httpServletRequest,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("amount") int amount){
        Cookie[] cookie = httpServletRequest.getCookies();
        String seller = new String();
        String result = new String();
        Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentificationSellerPage.autentification(cookies, key).equals("seller")){
            if (productService.findByName(name).get(0).getAmount() >= amount){
                productService.tranzactionDeleteAmount(name, amount);
                for(int i = 0; i < cookie.length; i++) {
                    if (cookie[i].getName().equals("JWT")) {
                        String jws = cookie[i].getValue();
                        String sources = Jwts.parserBuilder()
                                .setSigningKey(key)
                                .build()
                                .parseClaimsJws(jws)
                                .getBody()
                                .getSubject();
                        String[] values = sources.split("\\s+");
                        seller = values[0];
                    }
                }
                historyOfSellingService.createHistoryOfSelling(new HistoryOfSelling(
                        name, amount, seller
                ));
                result = "Оплачен товар " + name + " в количестве - " + amount;
            } else {
                result = "К сожалению, на складе не осталось товара";
            }
        }
        return result;
    }
}
