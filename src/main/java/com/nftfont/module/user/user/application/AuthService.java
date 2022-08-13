package com.nftfont.module.user.user.application;


import com.nftfont.core.configuration.jwt.JwtTokenProvider;
import com.nftfont.core.configuration.properties.AppProperties;
import com.nftfont.core.utils.CookieUtil;
import com.nftfont.module.user.user.domain.UserRefreshToken;
import com.nftfont.module.user.user.domain.UserRefreshTokenRepository;
import com.nftfont.module.user.user.presentation.request.AuthReqModel;
import com.nftfont.module.user.user.presentation.response.ApiResponse;
import com.nftfont.core.oauth.entity.UserPrincipal;
import com.nftfont.core.configuration.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.nftfont.core.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AppProperties appProperties;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    public ApiResponse authLogin(AuthReqModel authReqModel, HttpServletRequest request,
                                 HttpServletResponse response){

        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(authReqModel.getId(), authReqModel.getPassword()));

        String userId = authReqModel.getId();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date now = new Date();
        JwtToken accessToken = jwtTokenProvider.createAuthToken(userId,
                ((UserPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        JwtToken refreshToken = jwtTokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId);
        if (userRefreshToken == null) {
            // 없는 경우 새로 등록
            userRefreshToken = new UserRefreshToken(userId, refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        } else {
            // DB에 refresh 토큰 업데이트
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        }

        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return ApiResponse.success("token", accessToken.getToken());

    }

}
