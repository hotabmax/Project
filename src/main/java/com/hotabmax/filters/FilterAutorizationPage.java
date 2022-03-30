package com.hotabmax.filters;

import com.hotabmax.models.User;
import com.hotabmax.filters.resultForFilterAutorization.ResultForFilterAutorization;
import com.hotabmax.servicesJPA.UserService;
import com.hotabmax.models.Role;
import com.hotabmax.servicesJPA.RoleService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
@Service
public class FilterAutorizationPage extends AbstractFilterAutorization{

    public ResultForFilterAutorization autorization(Cookie[] cookies,
                                                    Key key, String AutorityName,
                                                    String AutorityPassword) {
        super.rolesAndUrl.put("Администратор", "redirect:/admin");
        super.rolesAndUrl.put("Логист", "redirect:/logist");
        super.rolesAndUrl.put("Продавец", "redirect:/seller");
        return super.autorization(cookies, key, AutorityName, AutorityPassword);
    }
}
