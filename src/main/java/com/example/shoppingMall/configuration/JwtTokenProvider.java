package com.example.shoppingMall.configuration;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtTokenProvider {

    @Value("${jwt.signerKey}")
    String JWT_SECRET ;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", Long.class);
    }

    public String getEmailFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            // Token không hợp lệ định dạng
        } catch (ExpiredJwtException ex) {
            // Token đã hết hạn
        } catch (UnsupportedJwtException ex) {
            // Token không được hỗ trợ
        } catch (IllegalArgumentException ex) {
            // Chuỗi claims trống
        }
        return false;
    }
}