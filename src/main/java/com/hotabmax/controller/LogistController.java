package com.hotabmax.controller;

import com.hotabmax.filters.FilterAdminDeleteHistoryOfPurchasePage;
import com.hotabmax.filters.FilterAutorizationPage;
import com.hotabmax.filters.FilterDomenPage;
import com.hotabmax.filters.FilterLogistPage;
import com.hotabmax.keygenerator.ClassOfKey;
import com.hotabmax.models.HistoryOfPurchase;
import com.hotabmax.models.Product;
import com.hotabmax.models.Sort;
import com.hotabmax.models.User;
import com.hotabmax.services.HistoryOfPurchaseService;
import com.hotabmax.services.ProductService;
import com.hotabmax.services.SortService;
import com.hotabmax.services.UserService;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LogistController {
    private Key key;
    private List<User> users = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Sort> sorts = new ArrayList<>();
    private List<HistoryOfPurchase> historyOfPurchases = new ArrayList<>();
    private Gson gson = new Gson();

    private FilterAutorizationPage filterAutorizationPage;
    private FilterDomenPage filterDomenPage;
    private FilterLogistPage filterLogistPage;
    private HistoryOfPurchaseService historyOfPurchaseService;
    private UserService userService;

    private ProductService productService;
    private SortService sortService;
    private ClassOfKey classOfKey;

    @Autowired
    public void setDependencies(
            @Qualifier("FilterAutorizationPage") FilterAutorizationPage filterAutorizationPage,
            @Qualifier("FilterDomenPage") FilterDomenPage filterDomenPage,
            @Qualifier("FilterLogistPage") FilterLogistPage filterLogistPage,
            @Qualifier("HistoryOfPurchaseService") HistoryOfPurchaseService historyOfPurchaseService,
            @Qualifier("UserService") UserService userService,
            @Qualifier("ProductService") ProductService productService,
            @Qualifier("SortService") SortService sortService,
            @Qualifier("ClassOfKey") ClassOfKey classOfKey
    ) {
        this.filterAutorizationPage = filterAutorizationPage;
        this.filterDomenPage = filterDomenPage;
        this.filterLogistPage = filterLogistPage;
        this.historyOfPurchaseService = historyOfPurchaseService;
        this.userService = userService;
        this.productService = productService;
        this.sortService = sortService;
        this.classOfKey = classOfKey;
    }

    @Bean
    public void setKeyLogist(){
        key = classOfKey.setKey();
    }


    @GetMapping("/")
    public String getHost(HttpServletRequest httpServletRequest) {
        return filterDomenPage.autentification(httpServletRequest, key);
    }

    @GetMapping("/exit")
    public String exitOff(HttpServletRequest httpServletRequest,
                          HttpServletResponse httpServletResponse) {
        Cookie[] cookies = httpServletRequest.getCookies();
        for(int i = 0; i < cookies.length; i++){
            if(cookies[i].getName().equals("JWT")){
                cookies[i].setValue("000000");
                httpServletResponse.addCookie(cookies[i]);
            }
        }
        return "autorization";
    }

    @PostMapping("/autorization")
    public  String loadAutorization(HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse,
                                       @RequestParam("name") String name,
                                       @RequestParam("password") String password) {
        String result;
        Cookie cookieWrite;
        result = filterAutorizationPage.autorizationIfCookieIsNull(httpServletRequest, key, name, password);
        cookieWrite = filterAutorizationPage.getCookie();
        if (cookieWrite != null) {
            httpServletResponse.addCookie(cookieWrite);
        }
        if (filterAutorizationPage.cookieChanged()) {
            httpServletResponse.addCookie(cookieWrite);
        }
        filterAutorizationPage.clearСookie();
        users.clear();
        return result;
    }


    @GetMapping("/logist")
    public String getAdmin(HttpServletRequest httpServletRequest){
        return filterLogistPage.autentification(httpServletRequest, key);
    }


    @PostMapping("/logist/getTableProductsBySort")
    @ResponseBody
    public String getTableProductsBySort(HttpServletRequest httpServletRequest,
                                               @RequestParam("nameSort") String nameSort) {
        if(filterLogistPage.autentification(httpServletRequest, key).equals("logist")){
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
        if(filterLogistPage.autentification(httpServletRequest, key).equals("logist")){
            products = productService.findByName(name);
        }
        return gson.toJson(products);
    }

    @PostMapping("/logist/getTableSorts")
    @ResponseBody
    public String getTablesSorts(HttpServletRequest httpServletRequest) {
        if(filterLogistPage.autentification(httpServletRequest, key).equals("logist")){
            sorts = sortService.findAll();
        }
        return gson.toJson(sorts);
    }

    /*@PostMapping("/admin/createUser")
    @ResponseBody
    public StringBuilder createUser(HttpServletRequest httpServletRequest,
                                   @RequestParam("name") String name,
                                   @RequestParam("password") int password,
                                   @RequestParam("role") String role) {
        StringBuilder json = new StringBuilder();
        if(filterAdminPage.autentification(httpServletRequest, key).equals("admin")){
            if (userService.findByName(name).size() == 0) {
                userService.createUser(new User(name, password, role));
            }
            json = new StringBuilder();
            users = userService.findAll();
            json.append(gson.toJson(users));
        }

        return json;
    }

    @GetMapping("/admin/deleteUser")
    public StringBuilder deleteUser(HttpServletRequest httpServletRequest,
                                   @RequestParam("name") String name) {
        StringBuilder json;
        if(filterAdminPage.autentification(httpServletRequest, key).equals("admin")){
            userService.deleteByName(name);
            json = new StringBuilder();
            users = userService.findAll();
            products = productService.findAll();
            sorts = sortService.findAll();
            json.append(gson.toJson(users));
            json.append(gson.toJson(products));
            json.append(gson.toJson(sorts));
        } else json = new StringBuilder(filterAdminPage.autentification(httpServletRequest, key));

        return json;
    }*/


    @PostMapping("/logist/tranzactionAddProductAmount")
    @ResponseBody
    public String tranzactionAddAmount(HttpServletRequest httpServletRequest,
                                              @RequestParam("name") String name,
                                              @RequestParam("amount") int amount){
        Cookie[] cookie = httpServletRequest.getCookies();
        String logist = new String();
        if(filterLogistPage.autentification(httpServletRequest, key).equals("logist")){
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
            products = productService.findAll();
        }
        return gson.toJson(products);
    }

}
