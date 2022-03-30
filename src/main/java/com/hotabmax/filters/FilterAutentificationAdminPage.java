package com.hotabmax.filters;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.security.Key;

@Service
public class FilterAutentificationAdminPage extends AbstractFilterAutentification{

    public String autentification(Cookie[] cookies, Key key) {
        super.rolesAndUrl.put("Администратор", "admin");
        super.rolesAndUrl.put("Логист", "redirect:/logist");
        super.rolesAndUrl.put("Продавец", "redirect:/seller");
        return super.autentification(cookies, key);
    }
}
