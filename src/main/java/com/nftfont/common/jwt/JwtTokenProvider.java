package com.nftfont.common.jwt;

import com.nftfont.common.exception.TokenValidFailedException;
import com.nftfont.module.user_pricipal.CustomUserDetailsService;
import com.nftfont.domain.userprincipal.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    private final Key key;
    private static final String AUTHORITIES_KEY = "role";
    public JwtTokenProvider(String secret){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public JwtToken createJwtToken(String id, Date expiry){
        return new JwtToken(id,expiry,key);
    }

    public JwtToken createJwtToken(String id, String role, Date expiry){
        return new JwtToken(id,role,expiry,key);
    }

    public JwtToken convertJwtToken(String token){
        return new JwtToken(token,key);
    }

    public Authentication getAuthentication(JwtToken jwtToken){
        if(jwtToken.validate()){
            Claims claims = jwtToken.getTokenClaims();

            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            UserPrincipal userPrincipal =(UserPrincipal) customUserDetailsService.loadUserByUsername(claims.getSubject());

            return new UsernamePasswordAuthenticationToken(userPrincipal, jwtToken,authorities);
        }

        throw new TokenValidFailedException();
    }
}
