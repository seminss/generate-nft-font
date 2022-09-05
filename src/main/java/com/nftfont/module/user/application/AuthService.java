package com.nftfont.module.user.application;


import com.nftfont.common.jwt.JwtTokenProvider;
import com.nftfont.config.properties.AppProperties;
import com.nftfont.module.file.image_file.application.ImageFileDto;
import com.nftfont.module.file.image_file.application.ImageFileService;
import com.nftfont.domain.user.User;
import com.nftfont.domain.user.UserRepository;
import com.nftfont.module.user.presentation.request.SignInWithTokenBody;
import com.nftfont.module.user.presentation.request.SignUpBody;
import com.nftfont.module.user.presentation.response.ApiResponse;
import com.nftfont.module.user.presentation.response.SuccessSignInDto;
import com.nftfont.domain.userprincipal.RoleType;
import com.nftfont.common.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.nftfont.common.utils.CookieUtil;
import com.nftfont.common.utils.HeaderUtil;
import com.nftfont.domain.user.UserRefreshToken;
import com.nftfont.domain.user.UserRefreshTokenRepository;
import com.nftfont.domain.userprincipal.UserPrincipal;
import com.nftfont.common.jwt.JwtToken;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final UserRepository userRepository;
    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";
    private final ImageFileService imageFileService;


    public void signUpWithWallet(SignUpBody body, MultipartFile profileI, MultipartFile backgroundI){
        ImageFileDto profile = imageFileService.saveProfileImage(profileI);
        ImageFileDto background = imageFileService.saveBackgroundImage(backgroundI);

        userRepository.save(User.of(body,profile.getImageUrl(), background.getImageUrl()));

    }


    public ApiResponse signInWithAccessToken(SignInWithTokenBody body, HttpServletRequest request, HttpServletResponse response){
        JwtToken jwtToken = jwtTokenProvider.convertJwtToken(body.getAccessToken());

        if(!jwtToken.validate()){
            return ApiResponse.invalidAccessToken();
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);

        UserPrincipal userPrincipal =(UserPrincipal)authentication.getPrincipal();


        Date now = new Date();
        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

        JwtToken refreshToken = jwtTokenProvider.createJwtToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(authentication.getName());

        if (userRefreshToken != null) {
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        } else {
            userRefreshToken = new UserRefreshToken(authentication.getName(),refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        }

        int cookieMaxAge = (int) refreshTokenExpiry / 60;

        CookieUtil.deleteCookie(request, response, OAuth2AuthorizationRequestBasedOnCookieRepository.REFRESH_TOKEN);
        CookieUtil.addCookie(response, OAuth2AuthorizationRequestBasedOnCookieRepository.REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return ApiResponse.success("userInfo", SuccessSignInDto.of(jwtToken.getToken(),userPrincipal.getId(),userPrincipal.getUserEmail()));

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
