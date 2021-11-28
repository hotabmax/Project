package com.hotabmax.controller;

import com.hotabmax.filters.FilterAdminPage;
import com.hotabmax.filters.FilterAutorizationPage;
import com.hotabmax.filters.FilterDomenPage;
import com.hotabmax.keygenerator.ClassOfKey;
import com.hotabmax.models.*;
import com.hotabmax.services.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    private Key key;
    private List<User> users = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Sort> sorts = new ArrayList<>();
    private List<Role> roles = new ArrayList<>();
    private List<HistoryOfPurchase> historyOfPurchases = new ArrayList<>();
    private List<HistoryOfSelling> historyOfSelling = new ArrayList<>();
    private Gson gson = new Gson();

    private FilterAutorizationPage filterAutorizationPage;
    private FilterDomenPage filterDomenPage;
    private FilterAdminPage filterAdminPage;
    private UserService userService;
    private ProductService productService;
    private SortService sortService;
    private RoleService roleService;
    private HistoryOfPurchaseService historyOfPurchaseService;
    private HistoryOfSellingService historyOfSellingService;
    private ClassOfKey classOfKey;
    @Autowired
    public void setDependencies(
            @Qualifier("FilterAutorizationPage") FilterAutorizationPage filterAutorizationPage,
            @Qualifier("FilterDomenPage") FilterDomenPage filterDomenPage,
            @Qualifier("FilterAdminPage") FilterAdminPage filterAdminPage,
            @Qualifier("UserService") UserService userService,
            @Qualifier("ProductService") ProductService productService,
            @Qualifier("SortService") SortService sortService,
            @Qualifier("RoleService") RoleService roleService,
            @Qualifier("HistoryOfPurchaseService") HistoryOfPurchaseService historyOfPurchaseService,
            @Qualifier("HistoryOfSellingService") HistoryOfSellingService historyOfSellingService,
            @Qualifier("ClassOfKey") ClassOfKey classOfKey

    ) {
        this.filterAutorizationPage = filterAutorizationPage;
        this.filterDomenPage = filterDomenPage;
        this.filterAdminPage = filterAdminPage;
        this.userService = userService;
        this.productService = productService;
        this.sortService = sortService;
        this.roleService = roleService;
        this.historyOfPurchaseService = historyOfPurchaseService;
        this.historyOfSellingService = historyOfSellingService;
        this.classOfKey = classOfKey;
    }

    @GetMapping("/qrParser")
    public String getQR(){
        return "qrParser";
    }

    @Bean
    public void setKeyAdmin(){
        key = classOfKey.setKey();
    }

    @GetMapping("/admin")
    public String getAdmin(HttpServletRequest httpServletRequest){
        return filterAdminPage.autentification(httpServletRequest, key);
    }

    @PostMapping("/admin/getTableUsersByRole")
    @ResponseBody
    public String getTableUsersByRole(HttpServletRequest httpServletRequest,
                                      @RequestParam("nameRole") String nameRole){
        if(filterAdminPage.autentification(httpServletRequest, key).equals("admin")){
            users = userService.findByRoleId((int) roleService.findByName(nameRole).get(0).getId());
        }
        return gson.toJson(users);
    }


    @PostMapping("/admin/createUser")
    @ResponseBody
    public String createUser(HttpServletRequest httpServletRequest,
                             @RequestParam("name") String name,
                             @RequestParam("password") int password,
                             @RequestParam("nameRole") String nameRole){
        if(filterAdminPage.autentification(httpServletRequest, key).equals("admin")){
            userService.createUser(new User(name, password,(int) roleService.findByName(nameRole).get(0).getId()));
            users = userService.findByRoleId((int) roleService.findByName(nameRole).get(0).getId());
        }
        return gson.toJson(users);
    }

    @PostMapping("/admin/deleteUser")
    @ResponseBody
    public String deleteUser(HttpServletRequest httpServletRequest,
                             @RequestParam("name") String name,
                             @RequestParam("nameRole") String nameRole){
        if(filterAdminPage.autentification(httpServletRequest, key).equals("admin")){
            userService.deleteByName(name);
            users = userService.findByRoleId((int) roleService.findByName(nameRole).get(0).getId());
        }
        return gson.toJson(users);
    }

    @PostMapping("/admin/getTableRoles")
    @ResponseBody
    public String getRoles(HttpServletRequest httpServletRequest){
        if(filterAdminPage.autentification(httpServletRequest, key).equals("admin")){
            roles = roleService.findAll();
        }
        return gson.toJson(roles);
    }

    @PostMapping("/admin/getHistoryOfPurchase")
    @ResponseBody
    public String getHistoryOfPurchase(HttpServletRequest httpServletRequest,
                                       @RequestParam("year") String year,
                                       @RequestParam("mounth") String mounth,
                                       @RequestParam("day") String day){
        if(filterAdminPage.autentification(httpServletRequest, key).equals("admin")){
            String date = year + "." + mounth + "." + day;
            historyOfPurchases = historyOfPurchaseService.findByDate(date);
        }
        return gson.toJson(historyOfPurchases);
    }

    @PostMapping("/admin/getHistoryOfSelling")
    @ResponseBody
    public String getHistoryOfSelling(HttpServletRequest httpServletRequest,
                                       @RequestParam("year") String year,
                                       @RequestParam("mounth") String mounth,
                                       @RequestParam("day") String day){
        if(filterAdminPage.autentification(httpServletRequest, key).equals("admin")){
            String date = year + "." + mounth + "." + day;
            historyOfSelling = historyOfSellingService.findByDate(date);
        }
        return gson.toJson(historyOfSelling);
    }

    @PostMapping("/admin/tranzactionDeleteProductAmountAndHistoryOfPurchase")
    @ResponseBody
    public void tranzactionDeleteProductAmountAndHistoryOfPurchase(HttpServletRequest httpServletRequest,
                                          @RequestParam("name") String name,
                                          @RequestParam("amount") int amount){
        if(filterAdminPage.autentification(httpServletRequest, key).equals("admin")){
            productService.tranzactionDeleteAmount(name, amount);
            historyOfPurchaseService.createHistoryOfPurchase(new HistoryOfPurchase(name, -amount, "!!!Списание!!!"));
        }
    }

    @PostMapping("/admin/tranzactionDeleteHistoryOfSelling")
    @ResponseBody
    public void tranzactionDeleteProductAmountAndHistoryOfSelling(HttpServletRequest httpServletRequest,
                                        @RequestParam("name") String name,
                                        @RequestParam("amount") int amount){
        if(filterAdminPage.autentification(httpServletRequest, key).equals("admin")){
            historyOfSellingService.createHistoryOfSelling(new HistoryOfSelling(name, -amount, "!!!Возврат!!!"));
        }
    }

    @PostMapping("/admin/getTableProductsBySort")
    @ResponseBody
    public String getTableProductsBySort(HttpServletRequest httpServletRequest,
                                              @RequestParam("nameSort") String nameSort) {
        if(filterAdminPage.autentification(httpServletRequest, key).equals("admin")){
            sorts = sortService.findByName(nameSort);
            int sortid = (int) sorts.get(0).getId();
            products = productService.findBySortId(sortid);
        }
        return gson.toJson(products);
    }

    @PostMapping("/admin/getTableProducts")
    @ResponseBody
    public String getTableProducts(HttpServletRequest httpServletRequest,
                                        @RequestParam("name") String name) {
        if(filterAdminPage.autentification(httpServletRequest, key).equals("admin")){
            products = productService.findByName(name);
        }
        return gson.toJson(products);
    }

    @PostMapping("/admin/getTableSorts")
    @ResponseBody
    public String getTablesSorts(HttpServletRequest httpServletRequest) {
        if(filterAdminPage.autentification(httpServletRequest, key).equals("admin")){
            sorts = sortService.findAll();
        }
        return gson.toJson(sorts);
    }

    @PostMapping("/admin/createSort")
    @ResponseBody
    public void createSort(HttpServletRequest httpServletRequest,
                           @RequestParam("name") String name) {

        if(filterAdminPage.autentification(httpServletRequest, key).equals("admin")){
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

    @PostMapping("/admin/deleteSort")
    @ResponseBody
    public void deleteSort(HttpServletRequest httpServletRequest,
                           @RequestParam("name") String name) {
        if(filterAdminPage.autentification(httpServletRequest, key).equals("admin")){
            sortService.deleteByName(name);
        }
    }

    @PostMapping("/admin/createProduct")
    @ResponseBody
    public String createProduct(HttpServletRequest httpServletRequest,
                                @RequestParam("name") String name,
                                @RequestParam("amount") int amount,
                                @RequestParam("code") int code,
                                @RequestParam("purchaseprice") int purchaseprice,
                                @RequestParam("sellingprice") int sellingprice,
                                @RequestParam("description") String description,
                                @RequestParam("nameSort") String nameSort) {
        if(filterAdminPage.autentification(httpServletRequest, key).equals("admin")){
            if(purchaseprice > 0 && sellingprice > 0 && amount >= 0){
                if (productService.findByName(name).size() == 0) {
                    productService.createProducts(new Product(name, code, amount,purchaseprice,
                            sellingprice, description,(int) sortService.findByName(nameSort).get(0).getId()));
                }
            }
            products = productService.findAll();
        }
        return gson.toJson(products);
    }

    @PostMapping("/admin/deleteProduct")
    @ResponseBody
    public String deleteProduct(HttpServletRequest httpServletRequest,
                                @RequestParam("name") String name) {
        if(filterAdminPage.autentification(httpServletRequest, key).equals("admin")){
            productService.deleteByName(name);
            products = productService.findAll();
        }
        return gson.toJson(products);
    }
}
