package com.hotabmax.controller.keygenerator;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

@Service
@Component("ClassOfKeys")
public class ClassOfKeys {
    private Key key;
    private KeyPair keyPair;

    public Key getKey() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        if(this.key == null){
            this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            this.keyPair = generator.generateKeyPair();
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        }
        return this.key;
    }
}
