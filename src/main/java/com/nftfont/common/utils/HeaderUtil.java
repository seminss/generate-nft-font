package com.nftfont.common.utils;

import com.nftfont.common.exception.AuthenticationException;

import javax.servlet.http.HttpServletRequest;

public class HeaderUtil {

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String HEADER_AUTHORIZATION2 = "access_token";
    private final static String TOKEN_PREFIX = "Bearer ";

    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);

        if (headerValue == null) {
            headerValue = request.getHeader(HEADER_AUTHORIZATION2);
        }
        if(headerValue == null){
            return null;
        }

        if (headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(TOKEN_PREFIX.length());
        }
        throw new AuthenticationException("access 인증 토큰이 없습니다.");
    }
}
