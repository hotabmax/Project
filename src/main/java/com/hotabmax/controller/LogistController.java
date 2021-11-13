package com.hotabmax.controller;

import com.hotabmax.filters.FilterAutorizationPage;
import com.hotabmax.filters.FilterDomenPage;
import com.hotabmax.filters.FilterLogistPage;
import com.hotabmax.keygenerator.ClassOfKey;
import com.hotabmax.models.Product;
import com.hotabmax.models.Sort;
import com.hotabmax.models.User;
import com.hotabmax.services.ProductService;
import com.hotabmax.services.SortService;
import com.hotabmax.services.UserService;
import com.google.gson.Gson;
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
    private Gson gson = new Gson();

    private FilterAutorizationPage filterAutorizationPage;
    private FilterDomenPage filterDomenPage;
    private FilterLogistPage filterLogistPage;
    private UserService userService;
    private ProductService productService;
    private SortService sortService;
    private ClassOfKey classOfKey;

    @Autowired
    public void setDependencies(
            @Qualifier("FilterAutorizationPage") FilterAutorizationPage filterAutorizationPage,
            @Qualifier("FilterDomenPage") FilterDomenPage filterDomenPage,
            @Qualifier("FilterLogistPage") FilterLogistPage filterLogistPage,
            @Qualifier("UserService") UserService userService,
            @Qualifier("ProductService") ProductService productService,
            @Qualifier("SortService") SortService sortService,
            @Qualifier("ClassOfKey") ClassOfKey classOfKey
    ) {
        this.filterAutorizationPage = filterAutorizationPage;
        this.filterDomenPage = filterDomenPage;
        this.filterLogistPage = filterLogistPage;
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
                                       @RequestParam("password") int password) {
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
        users.clear();
        return result;
    }


    @GetMapping("/logist")
    public String getAdmin(HttpServletRequest httpServletRequest){
        return filterLogistPage.autentification(httpServletRequest, key);
    }


    @PostMapping("/logist/getTableUsers")
    @ResponseBody
    public StringBuilder getAdminTableUsers(HttpServletRequest httpServletRequest) {
        StringBuilder json = new StringBuilder();
        if(filterLogistPage.autentification(httpServletRequest, key).equals("logist")){
            users = userService.findAll();
            json.append(gson.toJson(users));
        }
        return json;
    }

    @PostMapping("/logist/getTableProductsBySort")
    @ResponseBody
    public StringBuilder getAdminTableProductsBySort(HttpServletRequest httpServletRequest,
                                               @RequestParam("nameSort") String nameSort) {
        StringBuilder json = new StringBuilder();
        if(filterLogistPage.autentification(httpServletRequest, key).equals("logist")){
            sorts = sortService.findByName(nameSort);
            int sortid = (int) sorts.get(0).getId();
            products = productService.findBySortId(sortid);
            json.append(gson.toJson(products));
        }
        return json;
    }

    @PostMapping("/logist/getTableProducts")
    @ResponseBody
    public StringBuilder getAdminTableProducts(HttpServletRequest httpServletRequest,
                                               @RequestParam("name") String name) {
        StringBuilder json = new StringBuilder();
        if(filterLogistPage.autentification(httpServletRequest, key).equals("logist")){
            products = productService.findByName(name);
            json.append(gson.toJson(products));
        }
        return json;
    }

    @PostMapping("/logist/getTableSorts")
    @ResponseBody
    public StringBuilder getAdminTablesSorts(HttpServletRequest httpServletRequest) {
        StringBuilder json = new StringBuilder();
        if(filterLogistPage.autentification(httpServletRequest, key).equals("logist")){
            sorts = sortService.findAll();
            json.append(gson.toJson(sorts));
        }
        return json;
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

    @PostMapping("/logist/createSort")
    @ResponseBody
    public void createSort(HttpServletRequest httpServletRequest,
                                   @RequestParam("name") String name) {

        if(filterLogistPage.autentification(httpServletRequest, key).equals("logist")){
            if (userService.findByName(name).size() == 0) {
                sorts = sortService.findAll();
                int id = 0;
                for(int i=0; i < sorts.size(); i++){
                    if((int)sorts.get(i).getId() > id) id = (int)sorts.get(i).getId();
                }
                sortService.createSort(new Sort(++id, name));
            }
        }
    }

    @PostMapping("/logist/deleteSort")
    @ResponseBody
    public void deleteSort(HttpServletRequest httpServletRequest,
                                   @RequestParam("name") String name) {
        if(filterLogistPage.autentification(httpServletRequest, key).equals("logist")){
            sortService.deleteByName(name);
        }
    }

    @PostMapping("/logist/createProduct")
    @ResponseBody
    public StringBuilder createProduct(HttpServletRequest httpServletRequest,
                                   @RequestParam("name") String name,
                                   @RequestParam("amount") int amount,
                                       @RequestParam("purchaseprice") int purchaseprice,
                                       @RequestParam("sellingprice") int sellingprice,
                                   @RequestParam("description") String description,
                                   @RequestParam("nameSort") String nameSort) {
        StringBuilder json = new StringBuilder();
        if(filterLogistPage.autentification(httpServletRequest, key).equals("logist")){
            if(purchaseprice > 0 && sellingprice > 0 && amount >= 0){
                if (productService.findByName(name).size() == 0) {
                    productService.createProducts(new Product(name, amount,purchaseprice,
                            sellingprice, description,(int) sortService.findByName(nameSort).get(0).getId()));
                }
            }
            products = productService.findAll();
            json.append(gson.toJson(products));
        }
        return json;
    }

    @PostMapping("/logist/deleteProduct")
    @ResponseBody
    public StringBuilder deleteProduct(HttpServletRequest httpServletRequest,
                                   @RequestParam("name") String name) {
        StringBuilder json = new StringBuilder();
        if(filterLogistPage.autentification(httpServletRequest, key).equals("logist")){
            productService.deleteByName(name);
            products = productService.findAll();
            json.append(gson.toJson(products));
        }
        return json;
    }

    @PostMapping("/logist/tranzactionAddProductAmount")
    @ResponseBody
    public StringBuilder tranzactionAddAmount(HttpServletRequest httpServletRequest,
                                              @RequestParam("name") String name,
                                              @RequestParam("amount") int amount){
        StringBuilder json = new StringBuilder();
            if(filterLogistPage.autentification(httpServletRequest, key).equals("logist")){
                if(amount > 0){
                    productService.tranzactionAddAmount(name, amount);
                }
                products = productService.findAll();
                json.append(gson.toJson(products));
            }
        return json;
    }

    @PostMapping("/logist/tranzactionDeleteProductAmount")
    @ResponseBody
    public StringBuilder tranzactionDeleteAmount(HttpServletRequest httpServletRequest,
                                              @RequestParam("name") String name,
                                              @RequestParam("amount") int amount){
        StringBuilder json = new StringBuilder();
        if(filterLogistPage.autentification(httpServletRequest, key).equals("logist")){
            productService.tranzactionDeleteAmount(name, amount);
            products = productService.findAll();
            json.append(gson.toJson(products));
        }
        return json;
    }

}
