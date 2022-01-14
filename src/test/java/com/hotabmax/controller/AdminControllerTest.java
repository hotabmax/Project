package com.hotabmax.controller;

import com.hotabmax.controller.keygenerator.ClassOfKey;
import com.hotabmax.filters.resultForFilterAutorization.ResultForFilterAutorization;
import com.hotabmax.models.*;
import com.hotabmax.servicesJPA.*;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SortService sortService;
    @Autowired
    private HistoryOfPurchaseService historyOfPurchaseService;
    @Autowired
    private HistoryOfSellingService historyOfSellingService;
    @Autowired
    private ClassOfKey classOfKey;

    private MockMvc mockMvc;
    private Key key;
    private String jws;
    private Cookie cookie;
    private ResultForFilterAutorization resultForFilterAutorization;
    private String result;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void exitOff() throws Exception{
        key = classOfKey.setKey();
        userService.createUser(new User("Тест", "123",(int)roleService.findByName("Администратор").get(0).getId()));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        mockMvc.perform(MockMvcRequestBuilders.get("/exit")
                        .cookie(cookie)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(cookie().value("JWT", "000000"));
        userService.deleteByName("Тест");
    }

    @Test
    void getStatistic() throws Exception{
            key = classOfKey.setKey();
            userService.createUser(new User("Тест", "123",(int)roleService.findByName("Администратор").get(0).getId()));
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            mockMvc.perform(MockMvcRequestBuilders.post("/admin/getStatistic?year=2022&month=01")
                            .cookie(cookie)
                            .contentType(MediaType.TEXT_HTML))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Прибыль филиала за месяц (продажи - закупки) - 0р.<br/>"));
            userService.deleteByName("Тест");
    }

    @Test
    void getTableUsersByRole() throws Exception{
        key = classOfKey.setKey();
        userService.createUser(new User("Тест", "123",(int)roleService.findByName("Администратор").get(0).getId()));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/getTableUsersByRole?nameRole=Администратор")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name': 'Главный'}," +
                                                        "{'name':'Тест'}]"));
        userService.deleteByName("Тест");
    }

    @Test
    void createUser() throws Exception{
        key = classOfKey.setKey();
        userService.createUser(new User("Тест", "123",(int)roleService.findByName("Администратор").get(0).getId()));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/createUser?name=Тест2&password=123&nameRole=Администратор")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name': 'Главный'}," +
                        "{'name':'Тест'},{'name':'Тест2'}]"));
        userService.deleteByName("Тест");
        userService.deleteByName("Тест2");
    }

    @Test
    void deleteUser() throws Exception{
        key = classOfKey.setKey();
        userService.createUser(new User("Тест", "123",(int)roleService.findByName("Администратор").get(0).getId()));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/deleteUser?name=Тест&nameRole=Администратор")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name': 'Главный'}]"));
    }

    @Test
    void getRoles() throws Exception{
        key = classOfKey.setKey();
        userService.createUser(new User("Тест", "123",(int)roleService.findByName("Администратор").get(0).getId()));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/getTableRoles")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name': 'Администратор'}," +
                                                    "{'name': 'Продавец'}," +
                                                    "{'name': 'Логист'}]"));
        userService.deleteByName("Тест");
    }

    @Test
    void getHistoryOfPurchase() throws Exception{
        key = classOfKey.setKey();
        userService.createUser(new User("Тест", "123",(int)roleService.findByName("Администратор").get(0).getId()));
        historyOfPurchaseService.createHistoryOfPurchase(new HistoryOfPurchase("Тест", 50, "Тест"));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/getHistoryOfPurchase?year="+
                                        new SimpleDateFormat("yyyy").format(new Date()) +
                                "&month=" +
                                new SimpleDateFormat("MM").format(new Date()) +
                                "&day=" +
                                new SimpleDateFormat("dd").format(new Date())
                                )
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name': 'Тест', 'amount': 50, 'logistname': 'Тест'}]"));
        historyOfPurchaseService.deleteByDate(new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
        userService.deleteByName("Тест");
    }

    @Test
    void getHistoryOfSelling() throws Exception{
        key = classOfKey.setKey();
        userService.createUser(new User("Тест", "123",(int)roleService.findByName("Администратор").get(0).getId()));
        historyOfSellingService.createHistoryOfSelling(new HistoryOfSelling("Тест", 50, "Тест"));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/getHistoryOfSelling?year="+
                                new SimpleDateFormat("yyyy").format(new Date()) +
                                "&month=" +
                                new SimpleDateFormat("MM").format(new Date()) +
                                "&day=" +
                                new SimpleDateFormat("dd").format(new Date())
                        )
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name': 'Тест', 'amount': 50, 'sellername': 'Тест'}]"));
        historyOfSellingService.deleteByDate(new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
        userService.deleteByName("Тест");
    }

    @Test
    void tranzactionDeleteProductAmountAndHistoryOfPurchase() throws Exception{
        key = classOfKey.setKey();
        userService.createUser(new User("Тест", "123",(int)roleService.findByName("Администратор").get(0).getId()));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        sortService.createSort(new Sort(1, "Тест"));
        productService.createProducts(new Product("Тест",1,
                50, 100, 150,
                "Напиток",(int) sortService.findByName("Тест").get(0).getId()));
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/tranzactionDeleteHistoryOfPurchase?name=Тест&amount=10")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Успешно"));
        userService.deleteByName("Тест");
        productService.deleteByName("Тест");
        sortService.deleteByName("Тест");
    }

    @Test
    void tranzactionAddProductAmountAndHistoryOfSelling() throws Exception{
        key = classOfKey.setKey();
        userService.createUser(new User("Тест", "123",(int)roleService.findByName("Администратор").get(0).getId()));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        sortService.createSort(new Sort(1, "Тест"));
        productService.createProducts(new Product("Тест",1,
                50, 100, 150,
                "Напиток",(int) sortService.findByName("Тест").get(0).getId()));
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/tranzactionDeleteHistoryOfSelling?name=Тест&amount=10")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Успешно"));
        userService.deleteByName("Тест");
        productService.deleteByName("Тест");
        sortService.deleteByName("Тест");
    }

    @Test
    void getTableProductsBySort() throws Exception {
        key = classOfKey.setKey();
        userService.createUser(new User("Тест", "123",(int)roleService.findByName("Администратор").get(0).getId()));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        sortService.createSort(new Sort(1, "Тест"));
        productService.createProducts(new Product("Тест",1,
                50, 100, 150,
                "Напиток",(int) sortService.findByName("Тест").get(0).getId()));
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/getTableProductsBySort?nameSort=Тест")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name': 'Тест', 'code': 1, 'amount': 50," +
                        "'purchaseprice': 100, 'sellingprice': 150," +
                        " 'description': 'Напиток'}]"));
        userService.deleteByName("Тест");
        productService.deleteByName("Тест");
        sortService.deleteByName("Тест");
    }

    @Test
    void getTableProducts() throws Exception{
        key = classOfKey.setKey();
        userService.createUser(new User("Тест", "123",(int)roleService.findByName("Администратор").get(0).getId()));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        sortService.createSort(new Sort(1, "Тест"));
        productService.createProducts(new Product("Тест",1,
                50, 100, 150,
                "Напиток",(int) sortService.findByName("Тест").get(0).getId()));
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/getTableProducts?name=Тест")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name': 'Тест', 'code': 1, 'amount': 50," +
                        "'purchaseprice': 100, 'sellingprice': 150," +
                        " 'description': 'Напиток'}]"));
        userService.deleteByName("Тест");
        productService.deleteByName("Тест");
        sortService.deleteByName("Тест");
    }

    @Test
    void getTablesSorts() throws Exception{
        key = classOfKey.setKey();
        userService.createUser(new User("Тест", "123",(int)roleService.findByName("Администратор").get(0).getId()));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        sortService.createSort(new Sort(1, "Тест"));
        productService.createProducts(new Product("Тест",1,
                50, 100, 150,
                "Напиток",(int) sortService.findByName("Тест").get(0).getId()));
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/getTableSorts")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name': 'Тест'}]"));
        userService.deleteByName("Тест");
        productService.deleteByName("Тест");
        sortService.deleteByName("Тест");
    }

    @Test
    void createSort() throws Exception{
        key = classOfKey.setKey();
        userService.createUser(new User("Тест", "123",(int)roleService.findByName("Администратор").get(0).getId()));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/createSort?name=Тест")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Успешно"));
        userService.deleteByName("Тест");
        sortService.deleteByName("Тест");
    }

    @Test
    void deleteSort() throws Exception{
        key = classOfKey.setKey();
        userService.createUser(new User("Тест", "123",(int)roleService.findByName("Администратор").get(0).getId()));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        sortService.createSort(new Sort(1, "Тест"));
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/deleteSort?name=Тест")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Успешно"));
        userService.deleteByName("Тест");
    }

    @Test
    void createProduct() throws Exception{
        key = classOfKey.setKey();
        userService.createUser(new User("Тест", "123",(int)roleService.findByName("Администратор").get(0).getId()));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        sortService.createSort(new Sort(1, "Тест"));
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/createProduct" +
                                "?name=Тест&code=1&amount=50&purchaseprice=100&sellingprice=150" +
                                "&description=Напиток&nameSort=Тест")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name': 'Тест', 'code': 1, 'amount': 50," +
                        "'purchaseprice': 100, 'sellingprice': 150," +
                        " 'description': 'Напиток'}]"));
        userService.deleteByName("Тест");
        productService.deleteByName("Тест");
        sortService.deleteByName("Тест");
    }

    @Test
    void deleteProduct() throws Exception{
        key = classOfKey.setKey();
        userService.createUser(new User("Тест", "123",(int)roleService.findByName("Администратор").get(0).getId()));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        sortService.createSort(new Sort(1, "Тест"));
        productService.createProducts(new Product("Тест",1,
                50, 100, 150,
                "Напиток",(int) sortService.findByName("Тест").get(0).getId()));
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/deleteProduct?name=Тест")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
        userService.deleteByName("Тест");
        productService.deleteByName("Тест");
        sortService.deleteByName("Тест");
    }
}