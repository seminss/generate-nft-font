package com.nftfont.common.jwt;

import com.nftfont.common.utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

//
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("요청 uri:"+request.getRequestURI());
        System.out.println("주소"+request.getLocalAddr());
        System.out.println("이름"+request.getLocalName());
        System.out.println("포트"+request.getLocalPort());
        System.out.println("로컬"+request.getLocale());
        System.out.println("해더이름들"+request.getHeaderNames());
        String tokenStr = HeaderUtil.getAccessToken(request);
        JwtToken token =  tokenProvider.convertJwtToken(tokenStr);
        if(token.validate()){
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,response);

    }
}
