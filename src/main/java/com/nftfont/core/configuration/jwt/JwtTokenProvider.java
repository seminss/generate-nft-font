package com.nftfont.core.configuration.jwt;

import com.nftfont.core.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final Key key;
    private static final String AUTHORITIES_KEY = "role";

    public JwtTokenProvider(String secret){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public JwtToken createAuthToken(String id, Date expiry){
        return new JwtToken(id,expiry,key);
    }

    public JwtToken createAuthToken(String id, String role, Date expiry){
        return new JwtToken(id,role,expiry,key);
    }

    public JwtToken convertAuthToken(String token){
        return new JwtToken(token,key);
    }

    public Authentication getAuthentication(JwtToken jwtToken){
        if(jwtToken.validate()){
            Claims claims = jwtToken.getTokenClaims();

            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            log.debug("claims subject := [{}]",claims.getSubject());

            User principal = new User(claims.getSubject(), "", authorities);

            return new UsernamePasswordAuthenticationToken(principal, jwtToken,authorities);
        }

        throw new TokenValidFailedException();

    }
}
