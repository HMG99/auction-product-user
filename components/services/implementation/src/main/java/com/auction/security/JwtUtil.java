package com.auction.security;

import com.auction.entity.UserEntity;
import com.auction.exceptions.userexceptions.UserBadRequestException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "auctionkey";
    private final int accessTokenValidity = 300;
    private final JwtParser jwtParser;
    private final String TOKEN_PREFIX = "Bearer ";
    private final String TOKEN_HEADER = "Authorization";

    public JwtUtil() {
        this.jwtParser = Jwts.parser().setSigningKey(SECRET_KEY);
    }

    public String createToken(UserEntity userEntity) {
        Claims claims = Jwts.claims().setSubject(userEntity.getEmail());
        claims.put("first_name", userEntity.getName());
        claims.put("last_name", userEntity.getSurname());
        claims.put("role", userEntity.getRole());
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.SECONDS.toMillis(accessTokenValidity));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(tokenCreateTime)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims parsJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest request) {
        try {
            String token = resolveToken(request);
            if(token != null) {
                return parsJwtClaims(token);
            }
            return null;
        }
        catch (ExpiredJwtException ex) {
            throw new UserBadRequestException(ex.getMessage());
        }
        catch (Exception ex) {
            throw new UserBadRequestException("unauthorized");
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String tokenBearer = request.getHeader(TOKEN_HEADER);
        if(tokenBearer != null && tokenBearer.startsWith(TOKEN_PREFIX)){
            return tokenBearer.substring(TOKEN_PREFIX.length());
        }
        return null;
    }


    public boolean tokenValidation(Claims claims) {
        return claims.getExpiration().after(new Date());
    }

}