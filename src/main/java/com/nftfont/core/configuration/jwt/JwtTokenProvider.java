package com.nftfont.core.configuration.jwt;

import com.nftfont.core.exception.TokenValidFailedException;
import com.nftfont.core.oauth.service.CustomUserDetailsService;
import com.nftfont.module.user.user.domain.User;
import com.nftfont.module.user.user.domain.UserRepository;
import com.nftfont.module.user.user.domain.user_pricipal.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
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
