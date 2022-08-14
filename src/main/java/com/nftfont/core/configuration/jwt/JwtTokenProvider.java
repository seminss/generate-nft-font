package com.nftfont.core.configuration.jwt;

import com.nftfont.core.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
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

            log.debug("claims subject := [{}]",claims.getSubject());

            User principal = new User(claims.getSubject(), "", authorities);

            return new UsernamePasswordAuthenticationToken(principal, jwtToken,authorities);
        }

        throw new TokenValidFailedException();
    }
    public boolean  validate(String token){
        try{
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            // 2.
            Date expireDt = claims.getBody().getExpiration();
            Date nowDt = new Date();

            return !expireDt.before(nowDt);
        }catch (Exception e){
            return false;
        }
    }

}
