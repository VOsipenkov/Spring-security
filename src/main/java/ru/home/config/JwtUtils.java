package ru.home.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;

import static java.util.Objects.nonNull;

@Component
public class JwtUtils {

    public String generateToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.setSubject(username);
        claims.put("roles", role);
        Date now = new Date();

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setSubject(username)
            .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(1L).toInstant()))
            .signWith(SignatureAlgorithm.HS256, "SECRET")
            .compact();
    }

    public Boolean validateToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey("SECRET").parseClaimsJws(token);
        return claims.getBody().getExpiration().before(new Date());
    }

    public String getUsernameFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    private Claims getAllClaims(String token) {
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
