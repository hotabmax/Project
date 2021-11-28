package com.hotabmax.controller;

import com.hotabmax.filters.FilterAutorizationPage;
import com.hotabmax.filters.FilterDomenPage;
import com.hotabmax.filters.FilterLogistPage;
import com.hotabmax.filters.FilterSellerPage;
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

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SellerController {
    private Key key;
    private List<User> users = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Sort> sorts = new ArrayList<>();
    private Gson gson = new Gson();

    private FilterAutorizationPage filterAutorizationPage;
    private FilterDomenPage filterDomenPage;
    private FilterLogistPage filterAdminPage;
    private FilterSellerPage filterSellerPage;
    private UserService userService;
    private ProductService productService;
    private SortService sortService;
    private ClassOfKey classOfKey;

    @Autowired
    public void setDependencies(
            @Qualifier("FilterAutorizationPage") FilterAutorizationPage filterAutorizationPage,
            @Qualifier("FilterDomenPage") FilterDomenPage filterDomenPage,
            @Qualifier("FilterSallerPage") FilterSellerPage filterSallerPage,
            @Qualifier("UserService") UserService userService,
            @Qualifier("ProductService") ProductService productService,
            @Qualifier("SortService") SortService sortService,
            @Qualifier("ClassOfKey") ClassOfKey classOfKey
    ) {
        this.filterAutorizationPage = filterAutorizationPage;
        this.filterDomenPage = filterDomenPage;
        this.filterSellerPage = filterSallerPage;
        this.userService = userService;
        this.productService = productService;
        this.sortService = sortService;
        this.classOfKey = classOfKey;
    }
    @Bean
    public void setKeySaller(){
        key = classOfKey.setKey();
    }

    @GetMapping("/seller")
    public String getPage(HttpServletRequest httpServletRequest){
        return filterSellerPage.autentification(httpServletRequest, key);
    }

    @PostMapping("/seller/getTableSorts")
    @ResponseBody
    public String getAdminTablesSorts(HttpServletRequest httpServletRequest) {
        if(filterSellerPage.autentification(httpServletRequest, key).equals("seller")){
            sorts = sortService.findAll();
        }
        return gson.toJson(sorts);
    }

    @PostMapping("/seller/getTableProducts")
    @ResponseBody
    public String getAdminTableProducts(HttpServletRequest httpServletRequest,
                                               @RequestParam("name") String name) {
        if(filterSellerPage.autentification(httpServletRequest, key).equals("seller")){
            products = productService.findByName(name);
        }
        return gson.toJson(products);
    }

    @PostMapping("/seller/getTableProductsBySort")
    @ResponseBody
    public String getAdminTableProductsBySort(HttpServletRequest httpServletRequest,
                                               @RequestParam("nameSort") String nameSort) {
        if(filterSellerPage.autentification(httpServletRequest, key).equals("seller")){
            sorts = sortService.findByName(nameSort);
            int sortid = (int) sorts.get(0).getId();
            products = productService.findBySortId(sortid);
        }
        return gson.toJson(products);
    }

    @PostMapping("/seller/tranzactionDeleteProductAmount")
    @ResponseBody
    public StringBuilder tranzactionDeleteAmount(HttpServletRequest httpServletRequest,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("amount") int amount){
        StringBuilder stringBuilder = new StringBuilder();
        if(filterSellerPage.autentification(httpServletRequest, key).equals("seller")){
            productService.tranzactionDeleteAmount(name, amount);
        }
        stringBuilder.append("Оплачен товар " + name + " в количестве - " + amount);
        System.out.println(stringBuilder);
        return stringBuilder;
    }
}
