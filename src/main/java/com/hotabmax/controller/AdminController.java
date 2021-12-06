package com.hotabmax.controller;

import com.hotabmax.filters.*;
import com.hotabmax.keygenerator.ClassOfKey;
import com.hotabmax.models.*;
import com.hotabmax.services.*;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;

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
    private FilterAdminAddOrDeleteUserPage filterAdminAddOrDeleteUserPage;
    private FilterAdminDeleteHistoryOfPurchasePage filterAdminDeleteHistoryOfPurchasePage;
    private FilterAdminDeleteHistoryOfSellingPage filterAdminDeleteHistoryOfSellingPage;
    private FilterAdminAddOrDeleteNomenclaturePage filterAdminAddOrDeleteNomenclaturePage;
    private FilterAdminGetStatistic filterAdminGetStatistic;
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
            @Qualifier("FilterAdminAddOrDeleteUserPage") FilterAdminAddOrDeleteUserPage filterAdminAddOrDeleteUserPage,
            @Qualifier("FilterAdminDeleteHistoryOfPurchasePage") FilterAdminDeleteHistoryOfPurchasePage filterAdminDeleteHistoryOfPurchasePage,
            @Qualifier("FilterAdminDeleteHistoryOfSellingPage") FilterAdminDeleteHistoryOfSellingPage filterAdminDeleteHistoryOfSellingPage,
            @Qualifier("FilterAdminAddOrDeleteNomenclaturePage") FilterAdminAddOrDeleteNomenclaturePage filterAdminAddOrDeleteNomenclaturePage,
            @Qualifier("FilterAdminGetStatistic") FilterAdminGetStatistic filterAdminGetStatistic,
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
        this.filterAdminAddOrDeleteUserPage = filterAdminAddOrDeleteUserPage;
        this.filterAdminDeleteHistoryOfPurchasePage = filterAdminDeleteHistoryOfPurchasePage;
        this.filterAdminDeleteHistoryOfSellingPage = filterAdminDeleteHistoryOfSellingPage;
        this.filterAdminAddOrDeleteNomenclaturePage = filterAdminAddOrDeleteNomenclaturePage;
        this.filterAdminGetStatistic = filterAdminGetStatistic;
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

    @GetMapping("/adminAddOrDeleteUser")
    public String getAdminAddOrDeleteUser(HttpServletRequest httpServletRequest){
        return filterAdminAddOrDeleteUserPage.autentification(httpServletRequest, key);
    }

    @GetMapping("/adminDeleteHistoryOfPurchase")
    public String getAdminDeleteHistoryOfPurchase(HttpServletRequest httpServletRequest){
        return filterAdminDeleteHistoryOfPurchasePage.autentification(httpServletRequest, key);
    }

    @GetMapping("/adminDeleteHistoryOfSelling")
    public String getAdminDeleteHistoryOfSelling(HttpServletRequest httpServletRequest){
        return filterAdminDeleteHistoryOfSellingPage.autentification(httpServletRequest, key);
    }

    @GetMapping("/adminAddOrDeleteNomenclature")
    public String getAdminAddOrDeleteNomenclature(HttpServletRequest httpServletRequest){
        return filterAdminAddOrDeleteNomenclaturePage.autentification(httpServletRequest, key);
    }

    @GetMapping("/adminGetStatistic")
    public String getAdminGetStatistic(HttpServletRequest httpServletRequest){
        return filterAdminGetStatistic.autentification(httpServletRequest, key);
    }

    @PostMapping("/admin/getStatistic")
    @ResponseBody
    public String getStatistic(HttpServletRequest httpServletRequest,
                               @RequestParam("year") String year,
                               @RequestParam("mounth") String mounth){
        int money = 0;
        String result = new String();
        Map<String, Integer> nomenclature = new HashMap<>();
        if(filterAdminAddOrDeleteUserPage.autentification(httpServletRequest, key).equals("adminAddOrDeleteUser")){
            for(int i = 1; i < 32; i++){
                if(i/10 == 0){
                    historyOfSelling = historyOfSellingService.findByDate(year + "." + mounth + ".0" + i);
                } else historyOfSelling = historyOfSellingService.findByDate(year + "." + mounth + "." + i);
                for (int z = 0; z < historyOfSelling.size(); z++){
                    String name = historyOfSelling.get(z).getName();
                    int sell = productService.findByName(historyOfSelling.get(z).getName()).get(0).getSellingPrice();
                    int purchase = productService.findByName(historyOfSelling.get(z).getName()).get(0).getPurchasePrice();
                    int amount = historyOfSelling.get(z).getAmount();
                    if (nomenclature.containsKey(name)){
                        nomenclature.replace(name, nomenclature.get(name) + ((sell - purchase) * amount));
                    } else{
                        nomenclature.put(name, (sell - purchase) * amount);
                    }
                    money += (sell - purchase) * amount;
                }
            }
            Set<String> names = nomenclature.keySet();
            Iterator<String> iterator = names.iterator();
            result = "Прибыль филиала за месяц (продажи - закупки) - " + money + "р." + "<br/>";
            while (iterator.hasNext()){
                String name = iterator.next();
                int amount = nomenclature.get(name) / (productService.findByName(name).get(0).getSellingPrice() -
                        productService.findByName(name).get(0).getPurchasePrice());
                result += "Прибыль с продажи " + name + " ( " + amount + "шт.)" + " за месяц (продажи - закупки) - " + nomenclature.get(name) + "р." + "<br/>";
                System.out.println(result);
            }

        }
        return result;
    }

    @PostMapping("/admin/getTableUsersByRole")
    @ResponseBody
    public String getTableUsersByRole(HttpServletRequest httpServletRequest,
                                      @RequestParam("nameRole") String nameRole){
        if(filterAdminAddOrDeleteUserPage.autentification(httpServletRequest, key).equals("adminAddOrDeleteUser")){
            users = userService.findByRoleId((int) roleService.findByName(nameRole).get(0).getId());
        }
        return gson.toJson(users);
    }


    @PostMapping("/admin/createUser")
    @ResponseBody
    public String createUser(HttpServletRequest httpServletRequest,
                             @RequestParam("name") String name,
                             @RequestParam("password") String password,
                             @RequestParam("nameRole") String nameRole){
        if(filterAdminAddOrDeleteUserPage.autentification(httpServletRequest, key).equals("adminAddOrDeleteUser")){
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
        if(filterAdminAddOrDeleteUserPage.autentification(httpServletRequest, key).equals("adminAddOrDeleteUser")){
            userService.deleteByName(name);
            users = userService.findByRoleId((int) roleService.findByName(nameRole).get(0).getId());
        }
        return gson.toJson(users);
    }

    @PostMapping("/admin/getTableRoles")
    @ResponseBody
    public String getRoles(HttpServletRequest httpServletRequest){
        if(filterAdminAddOrDeleteUserPage.autentification(httpServletRequest, key).equals("adminAddOrDeleteUser")){
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
        if(filterAdminAddOrDeleteUserPage.autentification(httpServletRequest, key).equals("adminAddOrDeleteUser")){
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
        if(filterAdminAddOrDeleteUserPage.autentification(httpServletRequest, key).equals("adminAddOrDeleteUser")){
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
        if(filterAdminAddOrDeleteUserPage.autentification(httpServletRequest, key).equals("adminAddOrDeleteUser")){
            productService.tranzactionDeleteAmount(name, amount);
            Cookie[] cookie = httpServletRequest.getCookies();
            String admin = new String();
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
                    admin = values[0];
                }
            }
            historyOfPurchaseService.createHistoryOfPurchase(new HistoryOfPurchase(name, -amount, "!!!Списание!!! - "+admin));

        }
    }

    @PostMapping("/admin/tranzactionDeleteHistoryOfSelling")
    @ResponseBody
    public void tranzactionDeleteProductAmountAndHistoryOfSelling(HttpServletRequest httpServletRequest,
                                        @RequestParam("name") String name,
                                        @RequestParam("amount") int amount){
        if(filterAdminAddOrDeleteUserPage.autentification(httpServletRequest, key).equals("adminAddOrDeleteUser")){
            productService.tranzactionAddAmount(name, amount);
            Cookie[] cookie = httpServletRequest.getCookies();
            String admin = new String();
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
                    admin = values[0];
                }
            }
            historyOfSellingService.createHistoryOfSelling(new HistoryOfSelling(name, -amount, "!!!Возврат!!! - "+admin));
        }
    }

    @PostMapping("/admin/getTableProductsBySort")
    @ResponseBody
    public String getTableProductsBySort(HttpServletRequest httpServletRequest,
                                              @RequestParam("nameSort") String nameSort) {
        if(filterAdminAddOrDeleteUserPage.autentification(httpServletRequest, key).equals("adminAddOrDeleteUser")){
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
        if(filterAdminAddOrDeleteUserPage.autentification(httpServletRequest, key).equals("adminAddOrDeleteUser")){
            products = productService.findByName(name);
        }
        return gson.toJson(products);
    }

    @PostMapping("/admin/getTableSorts")
    @ResponseBody
    public String getTablesSorts(HttpServletRequest httpServletRequest) {
        if(filterAdminAddOrDeleteUserPage.autentification(httpServletRequest, key).equals("adminAddOrDeleteUser")){
            sorts = sortService.findAll();
        }
        return gson.toJson(sorts);
    }

    @PostMapping("/admin/createSort")
    @ResponseBody
    public void createSort(HttpServletRequest httpServletRequest,
                           @RequestParam("name") String name) {

        if(filterAdminAddOrDeleteUserPage.autentification(httpServletRequest, key).equals("adminAddOrDeleteUser")){
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
        if(filterAdminAddOrDeleteUserPage.autentification(httpServletRequest, key).equals("adminAddOrDeleteUser")){
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
        if(filterAdminAddOrDeleteUserPage.autentification(httpServletRequest, key).equals("adminAddOrDeleteUser")){
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
        if(filterAdminAddOrDeleteUserPage.autentification(httpServletRequest, key).equals("adminAddOrDeleteUser")){
            productService.deleteByName(name);
            products = productService.findAll();
        }
        return gson.toJson(products);
    }
}
