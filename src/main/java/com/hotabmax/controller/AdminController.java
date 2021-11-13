package com.hotabmax.controller;

import com.hotabmax.filters.FilterAutorizationPage;
import com.hotabmax.filters.FilterDomenPage;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    private Key key;
    private List<User> users = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Sort> sorts = new ArrayList<>();
    private Gson gson = new Gson();

    private FilterAutorizationPage filterAutorizationPage;
    private FilterDomenPage filterDomenPage;
    private UserService userService;
    private ProductService productService;
    private SortService sortService;
    private ClassOfKey classOfKey;
    @Autowired
    public void setDependencies(
            @Qualifier("FilterAutorizationPage") FilterAutorizationPage filterAutorizationPage,
            @Qualifier("FilterDomenPage") FilterDomenPage filterDomenPage,
            @Qualifier("UserService") UserService userService,
            @Qualifier("ProductService") ProductService productService,
            @Qualifier("SortService") SortService sortService,
            @Qualifier("ClassOfKey") ClassOfKey classOfKey
    ) {
        this.filterAutorizationPage = filterAutorizationPage;
        this.filterDomenPage = filterDomenPage;
        this.userService = userService;
        this.productService = productService;
        this.sortService = sortService;
        this.classOfKey = classOfKey;
    }

    @GetMapping("/qrParser")
    public String getQR(){
        return "qrParser";
    }

    /*@Bean
    public void setKeyAdmin(){
        key = classOfKey.setKey(); to do->
    }*/
}
