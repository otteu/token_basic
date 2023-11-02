package com.delivery.delivery_app.api.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.security.Key;

public class JwtTokenUtils {

    public static String getNickname(String token, String key) {
        return extractClaims(token, key).get("nickname", String.class);
    }

    public static boolean isExpired(String token, String key) {
        Date expiradDate = extractClaims(token, key).getExpiration();
        return expiradDate.before(new Date());
    }

    private static Claims extractClaims(String token, String key) {
        return Jwts.parserBuilder().setSigningKey(getKey(key))
                .build().parseClaimsJws(token).getBody();
    }

    public static String generateToken(String nickname, String key, long expiredTimeMs) {

        Claims claims = Jwts.claims();
        claims.put("nickname", nickname);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(getKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getKey(String  key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return  Keys.hmacShaKeyFor(keyBytes);
    }
}
