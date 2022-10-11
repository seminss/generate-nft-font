package com.nftfont.module.user.presentation;
import com.nftfont.common.dto.ApiResult;
import com.nftfont.config.properties.AppProperties;
import com.nftfont.module.user.application.AuthService;
import com.nftfont.module.user.dto.UserLoginInfo;
import com.nftfont.module.user.dto.AccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.SignatureException;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppProperties appProperties;
    @PostMapping("/users/signIn")
    public ApiResult<UserLoginInfo.ResponseDto> signIn(@RequestBody @Valid UserLoginInfo.RequestDto request){
        UserLoginInfo.ResponseDto responseDto = authService.signUpWithWallet(request);
        ApiResult<UserLoginInfo.ResponseDto> apiResult = ApiResult.success(responseDto);
        return apiResult;
    }

    @PostMapping("/users/auth/signature")
    public ApiResult<AccessTokenResponse.ResponseDto> verifySignIn(@RequestBody @Valid AccessTokenResponse.RequestDto request,
                                                                   HttpServletResponse response) throws SignatureException {
        AccessTokenResponse.ResponseDto responseDto = authService.verifySignature(request,response);
        ApiResult<AccessTokenResponse.ResponseDto> apiResult = ApiResult.success(responseDto);
        return apiResult;
    }

    @GetMapping("/users/auth/refresh")
    public ApiResult<AccessTokenResponse.ResponseDto> updateAccessToken(HttpServletRequest request, HttpServletResponse response, @CookieValue("refreshToken") Cookie cookie){
        String refreshToken = cookie.getValue();
        AccessTokenResponse.ResponseDto responseDto = authService.refreshToken(request, response, refreshToken);
        ApiResult<AccessTokenResponse.ResponseDto> apiResult = ApiResult.success(responseDto);
        return apiResult;
    }
}

