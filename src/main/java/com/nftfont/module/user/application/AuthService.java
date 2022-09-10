package com.nftfont.module.user.application;


import com.nftfont.common.jwt.JwtTokenProvider;
import com.nftfont.module.file.image_file.application.ImageFileService;
import com.nftfont.domain.user.user.UserRepository;
import com.nftfont.module.user.dto.UserCreation;
import com.nftfont.domain.user.user.UserRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final UserRepository userRepository;
    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";
    private final ImageFileService imageFileService;


    public UserCreation.ResponseDto signUpWithWallet(UserCreation.RequestDto body){
        WalletUtils.isValidAddress(body.getWalletAddress());
        Web3j web3j ;
        return null ;
    }


//    public UserCreation.ResponseDto signInWithAccessToken(SignInWithTokenBody body, HttpServletRequest request, HttpServletResponse response){
//        JwtToken jwtToken = jwtTokenProvider.convertJwtToken(body.getAccessToken());
//
//        if(!jwtToken.validate()){
//            throw new RuntimeException("dkadfska");
//        }
//
//        Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
//
//        UserPrincipal userPrincipal =(UserPrincipal)authentication.getPrincipal();
//
//
//        Date now = new Date();
//        long refreshTokenExpiry = THREE_DAYS_MSEC;
//
//        JwtToken refreshToken = jwtTokenProvider.createJwtToken(
//                appProperties.getAuth().getTokenSecret(),
//                new Date(now.getTime() + refreshTokenExpiry)
//        );
//
//        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(authentication.getName());
//
//        if (userRefreshToken != null) {
//            userRefreshToken.setRefreshToken(refreshToken.getToken());
//        } else {
//            userRefreshToken = new UserRefreshToken(authentication.getName(),refreshToken.getToken());
//            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
//        }
//
//        int cookieMaxAge = (int) refreshTokenExpiry / 60;
//
//        return UserCreation.ResponseDto.of(userPrincipal.getId());
//    }
//
//    public ApiResponse authRefresh(HttpServletRequest request, HttpServletResponse response){
//        // access token 확인
//        String accessToken = HeaderUtil.getAccessToken(request);
//        JwtToken jwtToken = jwtTokenProvider.convertJwtToken(accessToken);
//        if (!jwtToken.validate()) {
//            return ApiResponse.invalidAccessToken();
//        }
//
//        // expired access token 인지 확인
//        Claims claims = jwtToken.getExpiredTokenClaims();
//        if (claims == null) {
//            return ApiResponse.notExpiredTokenYet();
//        }
//
//        String userId = claims.getSubject();
//        RoleType roleType = RoleType.of(claims.get("role", String.class));
//
//        // refresh token
//        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
//                .map(Cookie::getValue)
//                .orElse((null));
//        JwtToken authRefreshToken = jwtTokenProvider.convertJwtToken(refreshToken);
//
//        if (authRefreshToken.validate()) {
//            return ApiResponse.invalidRefreshToken();
//        }
//
//        // userId refresh token 으로 DB 확인
//        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserIdAndRefreshToken(userId, refreshToken);
//        if (userRefreshToken == null) {
//            return ApiResponse.invalidRefreshToken();
//        }
//
//        Date now = new Date();
//        JwtToken newAccessToken = jwtTokenProvider.createJwtToken(userId, roleType.getCode(),
//                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
//        );
//
//        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();
//
//        // refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
//        if (validTime <= THREE_DAYS_MSEC) {
//            // refresh 토큰 설정
//            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
//
//            authRefreshToken = jwtTokenProvider.createJwtToken(
//                    appProperties.getAuth().getTokenSecret(),
//                    new Date(now.getTime() + refreshTokenExpiry)
//            );
//
//            // DB에 refresh 토큰 업데이트
//            userRefreshToken.setRefreshToken(authRefreshToken.getToken());
//
//            int cookieMaxAge = (int) refreshTokenExpiry / 60;
//            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
//            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
//        }
//
//        return ApiResponse.success("token", newAccessToken.getToken());
//
//    }



}
