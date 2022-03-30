package com.hotabmax.controller.keygenerator;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
@Component("ClassOfKey")
public class ClassOfKey {
    private Key key;

    public Key getKey() {
        if(this.key == null){
            this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        }
        return this.key;
    }
}
