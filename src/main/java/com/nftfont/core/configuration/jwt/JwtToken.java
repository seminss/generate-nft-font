package com.nftfont.core.configuration.jwt;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Getter
public class JwtToken {
    private final String token ;
    private final Key key;

    private static final String AUTHORITIES_KEY = "role";

    JwtToken(String id, String role, Date expiry, Key key){
        this.key = key;
        this.token = createAuthToken(id,role,expiry);
    }
    JwtToken(String id, Date expiry, Key key){
        this.key = key;
        this.token = createAuthToken(id, expiry);
    }

    private String createAuthToken(String id,Date expiry){
        return Jwts.builder()
                .setSubject(id)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }
    private String createAuthToken(String id,String role,Date expiry){
        return Jwts.builder()
                .setSubject(id)
                .claim(AUTHORITIES_KEY,role)
                .signWith(key,SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }
    public boolean validate(){
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims(){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (SecurityException e){

        }catch (MalformedJwtException e){

        }catch (ExpiredJwtException e){

        }catch (UnsupportedJwtException e){

        }catch (IllegalArgumentException e){

        }
        return null;
    }
    public Claims getExpiredTokenClaims(){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
        return null;
    }
}
