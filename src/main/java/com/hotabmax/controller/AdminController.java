package com.hotabmax.controller;

import com.hotabmax.filters.*;
import com.hotabmax.controller.keygenerator.ClassOfKey;
import com.hotabmax.filters.resultForFilterAutorization.ResultForFilterAutorization;
import com.hotabmax.models.*;
import com.hotabmax.servicesJPA.*;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import net.bytebuddy.description.method.ParameterList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminController {
    private Gson gson = new Gson();
    private Key key;
    private List<User> users = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Sort> sorts = new ArrayList<>();
    private List<Role> roles = new ArrayList<>();
    private List<HistoryOfPurchase> historyOfPurchases = new ArrayList<>();
    private List<HistoryOfSelling> historyOfSellings = new ArrayList<>();

    @Autowired
    private FilterAutentificationAdminPage filterAutentificationAdminPage;
    @Autowired
    private FilterAutentificationDomenPage filterAutentificationDomenPage;
    @Autowired
    private FilterAutorizationPage filterAutorizationPage;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SortService sortService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private HistoryOfPurchaseService historyOfPurchaseService;
    @Autowired
    private HistoryOfSellingService historyOfSellingService;
    @Autowired
    private ClassOfKey classOfKey;

    @Bean
    private void getKeyAdmin() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        key = classOfKey.getKey();
    }

    @GetMapping("/")
    public String getHost(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        return filterAutentificationDomenPage.autentification(cookies, key);
    }

    @PostMapping("/autorization")
    public String loadAutorization(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    @RequestParam("name") String name,
                                    @RequestParam("password") String password) {
        Cookie[] cookies = httpServletRequest.getCookies();
        ResultForFilterAutorization resultForFilterAutorization =
                filterAutorizationPage.autorization(cookies, key, name, password);
        if (resultForFilterAutorization.getCookie() != null) {
            httpServletResponse.addCookie(resultForFilterAutorization.getCookie());
        }
        return resultForFilterAutorization.getResultPage();
    }

    @GetMapping("/exit")
    public String exitOff(HttpServletRequest httpServletRequest,
                          HttpServletResponse httpServletResponse) {
        Cookie[] cookies = httpServletRequest.getCookies();
        for(int i = 0; i < cookies.length; i++){
            if(cookies[i].getName().equals("JWT")){
                cookies[i].setValue("111111");
                httpServletResponse.addCookie(cookies[i]);
            }
        }
        return "autorization";
    }

    @GetMapping("/admin")
    public String getAdminAddOrDeleteUserPage(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        String result = filterAutentificationAdminPage.autentification(cookies, key);
        if (filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            return "adminAddOrDeleteUser";
        }
        return result;
    }

    @GetMapping("/adminDeleteHistoryOfPurchase")
    public String getAdminDeleteHistoryOfPurchasePage(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        String result = filterAutentificationAdminPage.autentification(cookies, key);
        if (filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            return "adminDeleteHistoryOfPurchase";
        }
        return result;
    }

    @GetMapping("/adminDeleteHistoryOfSelling")
    public String getAdminDeleteHistoryOfSellingPage(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        String result = filterAutentificationAdminPage.autentification(cookies, key);
        if (filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            return "adminDeleteHistoryOfSelling";
        }
        return result;
    }

    @GetMapping("/adminAddOrDeleteNomenclature")
    public String getAdminAddOrDeleteNomenclaturePage(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        String result = filterAutentificationAdminPage.autentification(cookies, key);
        if (filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            return "adminAddOrDeleteNomenclature";
        }
        return result;
    }

    @GetMapping("/adminGetStatistic")
    public String getAdminStatisticPage(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        String result = filterAutentificationAdminPage.autentification(cookies, key);
        if (filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            return "adminGetStatistic";
        }
        return result;
    }

    @PostMapping("/admin/getStatistic")
    @ResponseBody
    public List<String> getStatistic(HttpServletRequest httpServletRequest,
                                     @RequestBody String data){
        HistoryOfSelling historyOfSelling = gson.fromJson(data, HistoryOfSelling.class);
        int money = 0;
        List<String> result = new ArrayList<>();
        Map<String, Integer> nomenclature = new HashMap<>();
        Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            for(int i = 1; i < 32; i++){
                if(i/10 == 0){
                    historyOfSellings = historyOfSellingService.findByDate(historyOfSelling.getDate() + ".0" + i);
                } else historyOfSellings = historyOfSellingService.findByDate(historyOfSelling.getDate() + "." + i);
                if (historyOfSellings.size() != 0){
                    for (int b = 0; b < historyOfSellings.size(); b++){
                        if (productService.findByName(historyOfSellings.get(b).getName()).size() != 0){
                            String name = historyOfSellings.get(b).getName();
                            int sell = productService.findByName(historyOfSellings.get(b).getName()).get(0).getSellingPrice();
                            int purchase = productService.findByName(historyOfSellings.get(b).getName()).get(0).getPurchasePrice();
                            int amount = historyOfSellings.get(b).getAmount();
                            if (nomenclature.containsKey(name)){
                                nomenclature.replace(name, nomenclature.get(name) + ((sell - purchase) * amount));
                            } else{
                                nomenclature.put(name, (sell - purchase) * amount);
                            }
                            money += (sell - purchase) * amount;
                        }
                    }
                }
            }
            Set<String> names = nomenclature.keySet();
            Iterator<String> iterator = names.iterator();
            result.add("Прибыль филиала за месяц (продажи - закупки) : " + money + "р.");
            while (iterator.hasNext()){
                String name = iterator.next();
                int amount = nomenclature.get(name) /
                        (productService.findByName(name).get(0).getSellingPrice() -
                                productService.findByName(name).get(0).getPurchasePrice());
                result.add("Прибыль с продажи " + name +
                        " ( " + amount + "шт.)" + " за месяц (продажи - закупки) : " +
                        nomenclature.get(name) + "р.");
            }
        }
        return result;
    }

    @PostMapping("/admin/getTableUsersByRole")
    @ResponseBody
    public String getTableUsersByRole(HttpServletRequest httpServletRequest,
                                      @RequestBody String data){
        Role role = gson.fromJson(data, Role.class);
        Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            users = userService.findByRoleId((int) roleService.findByName(role.getName()).get(0).getId());
        }
        return gson.toJson(users);
    }


    @PostMapping("/admin/createUser")
    @ResponseBody
    public String createUser(HttpServletRequest httpServletRequest,
                             @RequestBody String data){
        Cookie[] cookies = httpServletRequest.getCookies();
        User user = gson.fromJson(data, User.class);
        if(filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            userService.createUser(user);
            users = userService.findByRoleId(user.getRoleId());
        }
        return gson.toJson(users);
    }

    @PostMapping("/admin/deleteUser")
    @ResponseBody
    public String deleteUser(HttpServletRequest httpServletRequest,
                             @RequestBody String data) {
        Cookie[] cookies = httpServletRequest.getCookies();
        User user = gson.fromJson(data, User.class);
        int roleid = 0;
        if(filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            if (user.getName() != "-"){
                roleid = userService.findByName(user.getName()).get(0).getRoleId();
                userService.deleteByName(user.getName());
            }
            System.out.println(userService.findAll());
            if (userService.findAll().size() != 0){
                users = userService.findByRoleId(roleid);
            } else users.clear();
        }
        return gson.toJson(users);
    }

    @PostMapping("/admin/getTableRoles")
    @ResponseBody
    public String getRoles(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();

        if(filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            roles = roleService.findAll();
        }
        return gson.toJson(roles);
    }

    @PostMapping("/admin/getHistoryOfPurchase")
    @ResponseBody
    public String getHistoryOfPurchase(HttpServletRequest httpServletRequest,
                                       @RequestBody String data){
        HistoryOfPurchase historyOfPurchase = gson.fromJson(data, HistoryOfPurchase.class);
        Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            historyOfPurchases = historyOfPurchaseService.findByDate(historyOfPurchase.getDate());
        }
        return gson.toJson(historyOfPurchases);
    }

    @PostMapping("/admin/getHistoryOfSelling")
    @ResponseBody
    public String getHistoryOfSelling(HttpServletRequest httpServletRequest,
                                       @RequestBody String data){
        HistoryOfSelling historyOfSelling = gson.fromJson(data, HistoryOfSelling.class);
        Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            historyOfSellings = historyOfSellingService.findByDate(historyOfSelling.getDate());
        }
        return gson.toJson(historyOfSellings);
    }

    @PostMapping("/admin/tranzactionDeleteHistoryOfPurchase")
    @ResponseBody
    public String tranzactionDeleteProductAmountAndHistoryOfPurchase(HttpServletRequest httpServletRequest,
                                          @RequestBody String data){
        HistoryOfPurchase historyOfPurchase = gson.fromJson(data, HistoryOfPurchase.class);
        Cookie[] cookies = httpServletRequest.getCookies();
        String result = new String();
        if(filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            if(productService.findByName(historyOfPurchase.getName()).get(0).getAmount()
                    == historyOfPurchase.getAmount()){
                productService.tranzactionDeleteAmount(
                        historyOfPurchase.getName(), historyOfPurchase.getAmount());
            }

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
            historyOfPurchaseService.createHistoryOfPurchase(
                    new HistoryOfPurchase(
                            historyOfPurchase.getName(), -historyOfPurchase.getAmount(),
                            "!!!Списание!!! - "+admin));
            result = "Успешно";
        }
        return result;
    }

    @PostMapping("/admin/tranzactionDeleteHistoryOfSelling")
    @ResponseBody
    public String tranzactionAddProductAmountAndHistoryOfSelling(HttpServletRequest httpServletRequest,
                                        @RequestBody String data){
        HistoryOfSelling historyOfSelling = gson.fromJson(data, HistoryOfSelling.class);
        Cookie[] cookies = httpServletRequest.getCookies();
        String result = new String();
        if(filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            if(productService.findByName(historyOfSelling.getName()).get(0).getAmount()
                    == historyOfSelling.getAmount()){
                productService.tranzactionAddAmount(historyOfSelling.getName(),
                        historyOfSelling.getAmount());
            }
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
            historyOfSellingService.createHistoryOfSelling(
                    new HistoryOfSelling(historyOfSelling.getName(),
                            -historyOfSelling.getAmount(), "!!!Возврат!!! - "+admin));
        result = "Успешно";
        }
        return result;
    }

    @PostMapping("/admin/getTableProductsBySort")
    @ResponseBody
    public String getTableProductsBySort(HttpServletRequest httpServletRequest,
                                              @RequestBody String data) {
        Sort sort = gson.fromJson(data, Sort.class);
        Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            sorts = sortService.findByName(sort.getName());
            int sortid = (int) sorts.get(0).getId();
            products = productService.findBySortId(sortid);
        }
        return gson.toJson(products);
    }

    @PostMapping("/admin/getTableProducts")
    @ResponseBody
    public String getTableProducts(HttpServletRequest httpServletRequest,
                                        @RequestBody String data) {
        Product product = gson.fromJson(data, Product.class);
        Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            products = productService.findByName(product.getName());
        }
        return gson.toJson(products);
    }

    @PostMapping("/admin/getTableSorts")
    @ResponseBody
    public String getTablesSorts(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            sorts = sortService.findAll();
        }
        return gson.toJson(sorts);
    }

    @PostMapping("/admin/createSort")
    @ResponseBody
    public String createSort(HttpServletRequest httpServletRequest,
                             @RequestBody String data) {
        Sort sort = gson.fromJson(data, Sort.class);
        Cookie[] cookies = httpServletRequest.getCookies();
        String result = new String();
        if(filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            if (sortService.findByName(sort.getName()).size() == 0) {
                sorts = sortService.findAll();
                int id = 0;
                for(int i=0; i < sorts.size(); i++){
                    if((int)sorts.get(i).getId() > id) id = (int)sorts.get(i).getId();
                }
                sortService.createSort(new Sort(++id, sort.getName()));
            }
            result = "Успешно";
        }
        return result;
    }

    @PostMapping("/admin/deleteSort")
    @ResponseBody
    public String deleteSort(HttpServletRequest httpServletRequest,
                             @RequestBody String data) {
        Sort sort = gson.fromJson(data, Sort.class);
        Cookie[] cookies = httpServletRequest.getCookies();
        String result = new String();
        if(filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            sortService.deleteByName(sort.getName());
            result = "Успешно";
        }
        return result;
    }

    @PostMapping("/admin/createProduct")
    @ResponseBody
    public String createProduct(HttpServletRequest httpServletRequest,
                                @RequestBody String data) {
       Product product = gson.fromJson(data, Product.class);
       Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            if(product.getPurchasePrice() > 0 && product.getSellingPrice() > 0
                    && product.getAmount() >= 0){
                if (productService.findByName(product.getName()).size() == 0) {
                    productService.createProducts(product);
                }
            }
            products = productService.findBySortId(product.getSortid());
        }
        return gson.toJson(products);
    }

    @PostMapping("/admin/deleteProduct")
    @ResponseBody
    public String deleteProduct(HttpServletRequest httpServletRequest,
                                @RequestBody String data) {
        Product product = gson.fromJson(data, Product.class);
        Cookie[] cookies = httpServletRequest.getCookies();
        int sortid = 0;
        if(filterAutentificationAdminPage.autentification(cookies, key).equals("admin")){
            if (product.getName() != "-"){
                sortid = productService.findByName(product.getName()).get(0).getSortid();
                productService.deleteByName(product.getName());
            }
            if (productService.findAll().size() != 0){
                products = productService.findBySortId(sortid);
            } else products.clear();
        }
        return gson.toJson(products);
    }
}
