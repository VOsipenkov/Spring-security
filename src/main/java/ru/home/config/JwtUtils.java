package ru.home.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

@Component
public class JwtUtils {

    public String generateToken(Map<String, String> claims, String subject, Long expiration) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setExpiration(new Date(expiration))
            .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        var claims = getAllClaims(token);
        var tokenUsername = claims.getSubject();
        var tokenExpiration = claims.getExpiration();
        return userDetails.getUsername().equals(tokenUsername);
    }

    public Claims getAllClaims(String token) {
        var claims = Jwts.parser()
            .setSigningKey("SECRET")
            .parseClaimsJws(token)
            .getBody();
        if (nonNull(claims)) {
            return claims;
        }
        return (Claims) new HashMap<String, Object>();
    }
}
