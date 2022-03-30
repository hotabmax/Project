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
class FilterAutorizationPageTest {

    @Autowired
    private FilterAutorizationPage filterAutorizationPage;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Test
    void autorization() {
        try {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            String jws;
            Cookie cookie;
            Cookie[] cookies;
            ResultForFilterAutorization resultForFilterAutorization;

            userService.createUser(new User("Тест", "123",
                    (int)roleService.findByName("Администратор").get(0).getId()));
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            cookies = new Cookie[1];
            cookies[0] = cookie;
            resultForFilterAutorization =
                    filterAutorizationPage.autorization(cookies, key,
                            null, null);
            assertEquals("redirect:/admin", resultForFilterAutorization.getResultPage());

            cookies = null;
            resultForFilterAutorization = filterAutorizationPage.autorization(cookies, key,
                    "Тест", "123");
            jws = resultForFilterAutorization.getCookie().getValue();
            String sources = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jws)
                    .getBody()
                    .getSubject();
            String[] values = sources.split("\\s+");
            String name = values[0];
            String password = values[1];
            assertEquals("redirect:/admin", resultForFilterAutorization.getResultPage());
            assertEquals("Тест", name);
            assertEquals("123", password);
            userService.deleteByName("Тест");
            filterAutorizationPage = new FilterAutorizationPage();


            userService.createUser(new User("Тест", "123",
                    (int)roleService.findByName("Продавец").get(0).getId()));
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            cookies = new Cookie[1];
            cookies[0] = cookie;
            resultForFilterAutorization = filterAutorizationPage.autorization(cookies, key,
                    null, null);
            assertEquals("redirect:/seller", resultForFilterAutorization.getResultPage());

            cookies = null;
            resultForFilterAutorization = filterAutorizationPage.autorization(cookies, key,
                    "Тест", "123");
            jws = resultForFilterAutorization.getCookie().getValue();
            sources = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jws)
                    .getBody()
                    .getSubject();
            values = sources.split("\\s+");
            name = values[0];
            password = values[1];
            assertEquals("redirect:/seller", resultForFilterAutorization.getResultPage());
            assertEquals("Тест", name);
            assertEquals("123", password);
            userService.deleteByName("Тест");
            filterAutorizationPage = new FilterAutorizationPage();


            userService.createUser(new User("Тест", "123",
                    (int)roleService.findByName("Логист").get(0).getId()));
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            cookies = new Cookie[1];
            cookies[0] = cookie;
            resultForFilterAutorization = filterAutorizationPage.autorization(cookies, key,
                    null, null);
            assertEquals("redirect:/logist", resultForFilterAutorization.getResultPage());


            cookies = null;
            resultForFilterAutorization = filterAutorizationPage.autorization(cookies, key,
                    "Тест", "123");
            jws = resultForFilterAutorization.getCookie().getValue();
            sources = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jws)
                    .getBody()
                    .getSubject();
            values = sources.split("\\s+");
            name = values[0];
            password = values[1];
            assertEquals("redirect:/logist", resultForFilterAutorization.getResultPage());
            assertEquals("Тест", name);
            assertEquals("123", password);
            userService.deleteByName("Тест");
        } catch (Exception exc){
            userService.deleteByName("Тест");
        }


    }
}