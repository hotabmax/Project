package com.hotabmax.filters;

import com.hotabmax.models.User;
import com.hotabmax.services.UserService;
import com.hotabmax.models.Role;
import com.hotabmax.services.RoleService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@Component("FilterAutorizationPage")
public class FilterAutorizationPage {
    String[] values;
    Cookie cookie;

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setDependencies(
            @Qualifier("UserService") UserService userService,
            @Qualifier("RoleService") RoleService roleService
    ) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public String autorizationIfCookieIsNull(HttpServletRequest httpServletRequest,
                                             Key key, String AutorityName, String AutorityPassword) {

        Cookie[] cookie = httpServletRequest.getCookies();
        List<User> user = new ArrayList<>();
        List<Role> role = new ArrayList<>();
        String resultPage = "autorization";
        if(cookie == null) {
            user = userService.findByName(AutorityName);
            role.add(roleService.findById(user.get(0).getRoleId()));
            if (user.size() != 0) {
                if (user.get(0).getPassword().equals(AutorityPassword)) {
                    if (role.get(0).getName().equals("Администратор")){
                        String jws = Jwts.builder().setSubject(AutorityName+" "+AutorityPassword).signWith(key).compact();
                        Cookie cookieAdd = new Cookie("JWT", jws);
                        cookieAdd.setMaxAge(999999);
                        this.cookie = cookieAdd;
                        resultPage = "redirect:/adminAddOrDeleteUser";
                    } else if (role.get(0).getName().equals("Логист")) {
                        String jws = Jwts.builder().setSubject(AutorityName+" "+AutorityPassword).signWith(key).compact();
                        Cookie cookieAdd = new Cookie("JWT", jws);
                        cookieAdd.setMaxAge(999999);
                        this.cookie = cookieAdd;
                        resultPage = "redirect:/logist";
                    } else if (role.get(0).getName().equals("Продавец")) {
                        String jws = Jwts.builder().setSubject(AutorityName+" "+AutorityPassword).signWith(key).compact();
                        Cookie cookieAdd = new Cookie("JWT", jws);
                        cookieAdd.setMaxAge(999999);
                        this.cookie = cookieAdd;
                        resultPage = "redirect:/seller";
                    } else {
                        System.out.println("Пользователя не существует");
                        resultPage = "autorizationErr";
                    }
                } else resultPage = "autorizationErr";
            } else resultPage = "autorizationErr";
        }
        if (resultPage.equals("autorization"))
            return autentificationPage(httpServletRequest, key,
                                                                    AutorityName, AutorityPassword);
        else return resultPage;
    }

    public String autentificationPage(HttpServletRequest httpServletRequest,
                                  Key key, String AutorityName, String AutorityPassword) {
        String JWTname = new String();
        String JWTpassword = new String();
        Cookie[] cookie = httpServletRequest.getCookies();
        List<User> user = new ArrayList<>();
        List<Role> role = new ArrayList<>();
        String resultPage = "autorization";
        try {
            for(int i = 0; i < cookie.length; i++) {
                if(cookie[i].getName().equals("JWT")) {
                    String jws = cookie[i].getValue();
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

        user = userService.findByName(JWTname);
        role.add(roleService.findById(user.get(0).getRoleId()));
        if (user.size() != 0) {
            if (user.get(0).getPassword().equals(JWTpassword)) {
                if (role.get(0).getName().equals("Администратор")){
                    resultPage = "redirect:/adminAddOrDeleteUser";
                } else if (role.get(0).getName().equals("Логист")) {
                    System.out.println("Недостаточно прав для админа");
                    resultPage = "redirect:/logist";
                } else if (role.get(0).getName().equals("Продавец")) {
                    System.out.println("Недостаточно прав для админа");
                    resultPage = "redirect:/seller";
                } else {
                    System.out.println("Пользователя не существует");
                    resultPage = "autorizationErr";
                }
            }
        }

        if (resultPage.equals("autorization"))
            return autorization(key, AutorityName, AutorityPassword);
        else return resultPage;
    }

    public String autorization(Key key, String AutorityName, String AutorityPassword) {
        String resultPage = "autorization";
        List<User> user = new ArrayList<>();
        List<Role> role = new ArrayList<>();
        user = userService.findByName(AutorityName);
        System.out.println(user.get(0).getPassword());
        role.add(roleService.findById(user.get(0).getRoleId()));
            if (user.size() != 0) {
                if (user.get(0).getPassword().equals(AutorityPassword)) {
                    if (role.get(0).getName().equals("Администратор")){
                        String jws = Jwts.builder().setSubject(AutorityName+" "+AutorityPassword).signWith(key).compact();
                        Cookie cookieAdd = new Cookie("JWT", jws);
                        cookieAdd.setMaxAge(999999);
                        this.cookie = cookieAdd;
                        resultPage = "redirect:/adminAddOrDeleteUser";
                    } else if (role.get(0).getName().equals("Логист")) {
                        String jws = Jwts.builder().setSubject(AutorityName+" "+AutorityPassword).signWith(key).compact();
                        Cookie cookieAdd = new Cookie("JWT", jws);
                        cookieAdd.setMaxAge(999999);
                        this.cookie = cookieAdd;
                        resultPage = "redirect:/logist";
                    } else if (role.get(0).getName().equals("Продавец")) {
                        String jws = Jwts.builder().setSubject(AutorityName+" "+AutorityPassword).signWith(key).compact();
                        Cookie cookieAdd = new Cookie("JWT", jws);
                        cookieAdd.setMaxAge(999999);
                        this.cookie = cookieAdd;
                        resultPage = "redirect:/seller";
                    } else {
                        System.out.println("Пользователя не существует");
                        resultPage = "autorizationErr";
                    }
                } else resultPage = "autorizationErr";
            } else resultPage = "autorizationErr";
        return resultPage;
    }

    public Cookie getCookie() {
        return this.cookie;
    }

    public boolean cookieChanged(){
        if (this.cookie == null) return false;
        return false;
    }

    public void clearСookie(){
        this.cookie = null;
    }
}
