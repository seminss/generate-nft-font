package com.nftfont.module.user.user.application;


import com.nftfont.core.configuration.jwt.JwtTokenProvider;
import com.nftfont.core.configuration.properties.AppProperties;
import com.nftfont.core.oauth.entity.RoleType;
import com.nftfont.core.utils.CookieUtil;
import com.nftfont.core.utils.HeaderUtil;
import com.nftfont.module.user.user.domain.UserRefreshToken;
import com.nftfont.module.user.user.domain.UserRefreshTokenRepository;
import com.nftfont.module.user.user.presentation.request.AuthReqModel;
import com.nftfont.module.user.user.presentation.response.ApiResponse;
import com.nftfont.core.oauth.entity.UserPrincipal;
import com.nftfont.core.configuration.jwt.JwtToken;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {
    private final AppProperties appProperties;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";
    public ApiResponse authLogin(AuthReqModel authReqModel, HttpServletRequest request,
                                 HttpServletResponse response){

        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(authReqModel.getId(), authReqModel.getPassword()));

        String userId = authReqModel.getId();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date now = new Date();
        JwtToken jwtToken = jwtTokenProvider.createJwtToken(userId,
                ((UserPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        JwtToken refreshToken = jwtTokenProvider.createJwtToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId);

        if (userRefreshToken == null) {

            userRefreshToken = new UserRefreshToken(userId, refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);

        } else {
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        }

        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return ApiResponse.success("token", jwtToken.getToken());

    }

    public ApiResponse authRefresh(HttpServletRequest request, HttpServletResponse response){
        // access token 확인
        String accessToken = HeaderUtil.getAccessToken(request);
        JwtToken jwtToken = jwtTokenProvider.convertJwtToken(accessToken);
        if (!jwtToken.validate()) {
            return ApiResponse.invalidAccessToken();
        }

        // expired access token 인지 확인
        Claims claims = jwtToken.getExpiredTokenClaims();
        if (claims == null) {
            return ApiResponse.notExpiredTokenYet();
        }

        String userId = claims.getSubject();
        RoleType roleType = RoleType.of(claims.get("role", String.class));

        // refresh token
        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse((null));
        JwtToken authRefreshToken = jwtTokenProvider.convertJwtToken(refreshToken);

        if (authRefreshToken.validate()) {
            return ApiResponse.invalidRefreshToken();
        }

        // userId refresh token 으로 DB 확인
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserIdAndRefreshToken(userId, refreshToken);
        if (userRefreshToken == null) {
            return ApiResponse.invalidRefreshToken();
        }

        Date now = new Date();
        JwtToken newAccessToken = jwtTokenProvider.createJwtToken(userId, roleType.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        // refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
        if (validTime <= THREE_DAYS_MSEC) {
            // refresh 토큰 설정
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            authRefreshToken = jwtTokenProvider.createJwtToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + refreshTokenExpiry)
            );

            // DB에 refresh 토큰 업데이트
            userRefreshToken.setRefreshToken(authRefreshToken.getToken());

            int cookieMaxAge = (int) refreshTokenExpiry / 60;
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
        }

        return ApiResponse.success("token", newAccessToken.getToken());

    }

}
