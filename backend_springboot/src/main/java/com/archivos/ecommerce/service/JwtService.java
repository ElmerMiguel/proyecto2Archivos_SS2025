package com.archivos.ecommerce.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    // Se recomienda usar una clave secreta base64 más larga y fija (no regenerarla cada vez)
    // generar secret key base 64 en termina: "openssl rand -base64 64"
    private static final String SECRET_KEY = "bXlfdmVyeV9zZWNyZXRfa2V5X3N1cGVyX3NlY3JldF9rZXlfMTIzNDU2Nzg5MA==";
    private final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));

    private final long expiration = 86400000; // 24 horas

    public String generateToken(String email, String rol) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(email)
                .claims(Map.of("rol", rol))
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
