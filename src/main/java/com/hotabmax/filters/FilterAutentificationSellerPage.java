package com.hotabmax.filters;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.security.Key;

@Service
public class FilterAutentificationSellerPage extends AbstractFilterAutentification{

    public String autentification(Cookie[] cookies, Key key) {
        super.rolesAndUrl.put("Администратор", "redirect:/admin");
        super.rolesAndUrl.put("Логист", "redirect:/logist");
        super.rolesAndUrl.put("Продавец", "seller");
        return super.autentification(cookies, key);
    }
}
