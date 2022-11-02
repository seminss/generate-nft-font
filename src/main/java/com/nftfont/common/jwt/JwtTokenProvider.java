package com.nftfont.common.jwt;

import com.nftfont.common.exception.TokenValidFailedException;
import com.nftfont.config.properties.AppProperties;
import com.nftfont.module.user.application.CustomUserDetailsService;
import com.nftfont.module.user.domain.userprincipal.UserPrincipal;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final CustomUserDetailsService customUserDetailsService;
    private static final String AUTHORITIES_KEY = "role";
    private final AppProperties appProperties;
    public JwtToken createJwtToken(String id, Date expiry){
        return new JwtToken(id,expiry,appProperties.getAuth().getTokenSecret());
    }

    public JwtToken createJwtToken(String id, String role, Date expiry){
        return new JwtToken(id,role,expiry,appProperties.getAuth().getTokenSecret());
    }

    public JwtToken convertJwtToken(String token){
        return new JwtToken(token);
    }

    public Authentication getAuthentication(JwtToken jwtToken){
        Claims claims;

        if(jwtToken.validate()){
            try {
                claims = jwtToken.getTokenClaims();
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }

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
