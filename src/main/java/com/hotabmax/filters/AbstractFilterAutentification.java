package com.hotabmax.filters;

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

public abstract class AbstractFilterAutentification {
    Map<String, String> rolesAndUrl = new HashMap<>();
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    public String autentification(Cookie[] cookies, Key key) {
        List<User> user = new ArrayList<>();
        List<Role> role = new ArrayList<>();
        String resultPage = "autorizationErr";
        if (cookies != null){
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
                        String[] values = sources.split("\\s+");
                        String JWTname = values[0];
                        String JWTpassword = values[1];
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

                    }
                }
            } catch (JwtException exc) {
                resultPage = "autorizationErr";
            }
        } else resultPage = "autorizationErr";
        return resultPage;
    }
}
