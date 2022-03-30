package com.hotabmax.filters;

import com.hotabmax.filters.resultForFilterAutorization.ResultForFilterAutorization;
import com.hotabmax.models.Role;
import com.hotabmax.models.User;
import com.hotabmax.servicesJPA.RoleService;
import com.hotabmax.servicesJPA.UserService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractFilterAutorization {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    Map<String, String> rolesAndUrl = new HashMap<>();

    public ResultForFilterAutorization autorization(Cookie[] cookies,
                                                    Key key, String AutorityName, String AutorityPassword) {
        List<User> user = new ArrayList<>();
        List<Role> role = new ArrayList<>();
        String resultPage = "default";
        Cookie cookieResult = null;
        if(cookies == null) {
            user = userService.findByName(AutorityName);
            role.add(roleService.findById(user.get(0).getRoleId()));
            if (user.size() != 0) {
                if (user.get(0).getPassword().equals(AutorityPassword)) {
                    for (int z = 0; z < rolesAndUrl.size(); z++){
                        try {
                            resultPage = rolesAndUrl.get(role.get(0).getName());
                            String jws = Jwts.builder().setSubject(AutorityName+" "+AutorityPassword).signWith(key).compact();
                            System.out.println(jws);
                            cookieResult = new Cookie("JWT", jws);
                            cookieResult.setMaxAge(999999);
                            break;
                        } catch (NullPointerException exc){
                            resultPage = "default";
                        }
                    }
                } else resultPage = "autorizationErr";
            } else resultPage = "autorizationErr";
        }
        ResultForFilterAutorization resultForFilterAutorization = new ResultForFilterAutorization(resultPage, cookieResult);
        if (resultPage.equals("default"))
            return autentificationPage(cookies, key, AutorityName, AutorityPassword);
        else {
            return resultForFilterAutorization;
        }
    }

    private ResultForFilterAutorization autentificationPage(Cookie[] cookies,
                                                            Key key, String AutorityName, String AutorityPassword) {
        List<User> user = new ArrayList<>();
        List<Role> role = new ArrayList<>();
        String resultPage = "default";
        Cookie cookieResult = null;
        String JWTname = null;
        String JWTpassword = null;
        String[] values;
        try {
            for(int i = 0; i < cookies.length; i++) {
                if(cookies[i].getName().equals("JWT")) {
                    String jws = cookies[i].getValue();
                    String sources = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(jws)
                            .getBody()
                            .getSubject();
                    values = sources.split("\\s+");
                    JWTname = values[0];
                    JWTpassword = values[1];
                }
            }
        } catch (JwtException exc) {
            System.out.println("Куки недействителен");
        }
        if (JWTname != null && JWTpassword != null){
            user = userService.findByName(JWTname);
            if (user.size() != 0) {
                role.add(roleService.findById(user.get(0).getRoleId()));
                if (user.get(0).getPassword().equals(JWTpassword)) {
                    for (int z = 0; z < rolesAndUrl.size(); z++){
                        try {
                            resultPage = rolesAndUrl.get(role.get(0).getName());
                            break;
                        } catch (NullPointerException exc){
                            resultPage = "default";
                        }
                    }
                }
            }
        }

        ResultForFilterAutorization resultForFilterAutorization =
                new ResultForFilterAutorization(resultPage, cookieResult);
        if (resultPage.equals("default")){
            System.gc();
            return autorizationIfCookieIsNotNull(key, AutorityName, AutorityPassword);
        }
        else{
            return resultForFilterAutorization;
        }
    }

    private ResultForFilterAutorization autorizationIfCookieIsNotNull(Key key, String AutorityName, String AutorityPassword) {
        List<User> user = new ArrayList<>();
        List<Role> role = new ArrayList<>();
        String resultPage = "default";
        Cookie cookieResult = null;
        user = userService.findByName(AutorityName);
        role.add(roleService.findById(user.get(0).getRoleId()));
        if (user.size() != 0) {
            if (user.get(0).getPassword().equals(AutorityPassword)) {
                for (int z = 0; z < rolesAndUrl.size(); z++){
                    if (rolesAndUrl.equals(role.get(0).getName())){
                        String jws = Jwts.builder().setSubject(AutorityName+" "+AutorityPassword).signWith(key).compact();
                        cookieResult = new Cookie("JWT", jws);
                        cookieResult.setMaxAge(999999);
                    }
                    try {
                        resultPage = rolesAndUrl.get(role.get(0).getName());
                        String jws = Jwts.builder().setSubject(AutorityName+" "+AutorityPassword).signWith(key).compact();
                        cookieResult = new Cookie("JWT", jws);
                        cookieResult.setMaxAge(999999);
                        break;
                    } catch (NullPointerException exc){
                        resultPage = "autorizationErr";
                    }
                }
            } else resultPage = "autorizationErr";
        } else resultPage = "autorizationErr";
        ResultForFilterAutorization resultForFilterAutorization =
                new ResultForFilterAutorization(resultPage, cookieResult);
        return resultForFilterAutorization;
    }
}
