package com.nftfont.common.jwt;

import com.nftfont.config.properties.AppProperties;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Getter
@Setter
public class JwtToken {
    private final String token ;

    private String secret;

    private static final String AUTHORITIES_KEY = "role";

    JwtToken(String id, String role, Date expiry,String secret){
        this.secret=secret;
        this.token = createAuthToken(id,role,expiry);
    }
    JwtToken(String id, Date expiry,String secret){
        this.secret=secret;
        this.token = createAuthToken(id, expiry);
    }
    private String createAuthToken(String id,Date expiry){
        return Jwts.builder()
                .setSubject(id)
                .signWith(SignatureAlgorithm.HS256,secret)
                .setExpiration(expiry)
                .compact();
    }
    private String createAuthToken(String id,String role,Date expiry){
        return Jwts.builder()
                .setSubject(id)
                .claim(AUTHORITIES_KEY,role)
                .signWith(SignatureAlgorithm.HS256,secret)
                .setExpiration(expiry)
                .compact();
    }
    public boolean validate(){
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims(){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (SecurityException e){
            System.out.println(e.getMessage());
        }catch (MalformedJwtException e){
            System.out.println(e.getMessage());
        }catch (ExpiredJwtException e){
            System.out.println(e.getMessage());
        }catch (UnsupportedJwtException e){
            System.out.println(e.getMessage());
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Claims getExpiredTokenClaims(){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
        return null;
    }
}
