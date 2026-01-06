package com.example.mediaPlayer.demo.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Component
public class JwtUtil {

    // 🔐 SECRET KEY (MIN 64 CHAR FOR HS512)
    private static final String SECRET =
            "chetanamahant1234567890chetanamahant1234567890chetanamahant1234567890";

    private final Key key =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 hours

    // ✅ GENERATE JWT TOKEN
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                )
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // ✅ EXTRACT USERNAME (SAFE METHOD)
    public String extractUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    // ✅ VALIDATE TOKEN (EXPIRY + SIGNATURE)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}