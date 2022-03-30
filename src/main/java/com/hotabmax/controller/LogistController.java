package com.hotabmax.controller;

import com.hotabmax.filters.FilterAutentificationLogistPage;
import com.hotabmax.controller.keygenerator.ClassOfKey;
import com.hotabmax.models.HistoryOfPurchase;
import com.hotabmax.models.Product;
import com.hotabmax.models.Sort;
import com.hotabmax.models.User;
import com.hotabmax.servicesJPA.HistoryOfPurchaseService;
import com.hotabmax.servicesJPA.ProductService;
import com.hotabmax.servicesJPA.SortService;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LogistController {
    private Gson gson = new Gson();
    private Key key;
    private List<User> users = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Sort> sorts = new ArrayList<>();
    private List<HistoryOfPurchase> historyOfPurchases = new ArrayList<>();

    @Autowired
    private FilterAutentificationLogistPage filterAutentificationLogistPage;
    @Autowired
    private HistoryOfPurchaseService historyOfPurchaseService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SortService sortService;
    @Autowired
    private ClassOfKey classOfKey;

    @Bean
    private void getKeyLogist(){
        key = classOfKey.getKey();
    }


    @GetMapping("/logist")
    public String getAdmin(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        return filterAutentificationLogistPage.autentification(cookies, key);
    }


    @PostMapping("/logist/getTableProductsBySort")
    @ResponseBody
    public String getTableProductsBySort(HttpServletRequest httpServletRequest,
                                               @RequestParam("nameSort") String nameSort) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentificationLogistPage.autentification(cookies, key).equals("logist")){
            sorts = sortService.findByName(nameSort);
            int sortid = (int) sorts.get(0).getId();
            products = productService.findBySortId(sortid);
        }
        return gson.toJson(products);
    }

    @PostMapping("/logist/getTableProducts")
    @ResponseBody
    public String getTableProducts(HttpServletRequest httpServletRequest,
                                               @RequestParam("name") String name) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentificationLogistPage.autentification(cookies, key).equals("logist")){
            products = productService.findByName(name);
        }
        return gson.toJson(products);
    }

    @PostMapping("/logist/getTableSorts")
    @ResponseBody
    public String getTablesSorts(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentificationLogistPage.autentification(cookies, key).equals("logist")){
            sorts = sortService.findAll();
        }
        return gson.toJson(sorts);
    }

    @PostMapping("/logist/tranzactionAddProductAmount")
    @ResponseBody
    public String tranzactionAddAmount(HttpServletRequest httpServletRequest,
                                              @RequestParam("name") String name,
                                              @RequestParam("amount") int amount){
        Cookie[] cookie = httpServletRequest.getCookies();
        String logist = new String();
        Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentificationLogistPage.autentification(cookies, key).equals("logist")){
            if(amount > 0){
                productService.tranzactionAddAmount(name, amount);
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
                        logist = values[0];
                    }
                }
                historyOfPurchaseService.createHistoryOfPurchase(new HistoryOfPurchase(
                        name, amount, logist
                ));
            }
            if (productService.findByName(name).size() != 0){
                products = productService.findBySortId(productService.findByName(name).get(0).getSortid());
            }
        }
        return gson.toJson(products);
    }

}
