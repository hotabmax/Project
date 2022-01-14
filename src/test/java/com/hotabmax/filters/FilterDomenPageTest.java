package com.hotabmax.filters;

import com.hotabmax.filters.resultForFilterAutorization.ResultForFilterAutorization;
import com.hotabmax.models.User;
import com.hotabmax.servicesJPA.RoleService;
import com.hotabmax.servicesJPA.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.Cookie;
import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FilterDomenPageTest {

    @Autowired
    private FilterDomenPage filterDomenPage;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Test
    void autentification() {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String jws;
        Cookie cookie;
        Cookie[] cookies;
        ResultForFilterAutorization resultForFilterAutorization;
        String result;

        userService.createUser(new User("Тест", "123",
                (int)roleService.findByName("Администратор").get(0).getId()));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        cookies = new Cookie[1];
        cookies[0] = cookie;
        result = filterDomenPage.autentification(cookies, key);

        assertEquals("redirect:/admin", result);

        userService.deleteByName("Тест");

        userService.createUser(new User("Тест", "123",
                (int)roleService.findByName("Продавец").get(0).getId()));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        cookies = new Cookie[1];
        cookies[0] = cookie;
        result = filterDomenPage.autentification(cookies, key);

        assertEquals("redirect:/seller", result);

        userService.deleteByName("Тест");

        userService.createUser(new User("Тест", "123",
                (int)roleService.findByName("Логист").get(0).getId()));
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        cookies = new Cookie[1];
        cookies[0] = cookie;
        result = filterDomenPage.autentification(cookies, key);

        assertEquals("redirect:/logist", result);

        userService.deleteByName("Тест");
    }
}