package com.jwt.util.utils;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Base64;

public class KeyUtility {

    public static String getSecretKey() {
        SecretKey key = Jwts.SIG.HS512.key().build();
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}
