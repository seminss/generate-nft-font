package com.nftfont.core.configuration.jwt;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component //?
public class JwtTokenProvider {

    private String secretKey = "뭘로할까";

    private final static long TOKEN_VALID_MILLISECOND = 1000L;

    public String createToken(){
        return null;
    }

    public Authentication getAuthentication(){

        return null;
    }
    public boolean validate(){
        return true;
    }
    public String getUserIdFromJwt(String token){
        return null ;
    }

}
