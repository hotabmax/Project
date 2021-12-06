package com.hotabmax.filters;

import com.hotabmax.models.Role;
import com.hotabmax.models.User;
import com.hotabmax.services.RoleService;
import com.hotabmax.services.UserService;
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

@Component("FilterAdminDeleteHistoryOfSellingPage")
public class FilterAdminDeleteHistoryOfSellingPage {
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

    public String autentification(HttpServletRequest httpServletRequest, Key key) {
        Cookie[] cookie = httpServletRequest.getCookies();
        String resultPage = "autorization";
        List<User> user = new ArrayList<>();
        List<Role> role = new ArrayList<>();

        if (cookie != null) {
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
                        String[] values = sources.split("\\s+");
                        String name = values[0];
                        String password = values[1];
                        user = userService.findByName(name);
                        role.add(roleService.findById(user.get(0).getRoleId()));
                        if (user.size() != 0) {
                            if (user.get(0).getPassword().equals(password)) {
                                if (role.get(0).getName().equals("Администратор")){
                                    resultPage = "adminDeleteHistoryOfSelling";
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
                    }
                }
            } catch (JwtException exc) {
                System.out.println("Куки недействителен");
                resultPage = "autorizationErr";
            }
        } else resultPage = "autorizationErr";


        return resultPage;
    }
}
