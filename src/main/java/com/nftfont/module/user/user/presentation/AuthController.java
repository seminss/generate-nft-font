package com.nftfont.module.user.user.presentation;

import com.nftfont.core.configuration.jwt.JwtTokenProvider;
import com.nftfont.core.configuration.properties.AppProperties;
import com.nftfont.core.utils.CookieUtil;
import com.nftfont.core.utils.HeaderUtil;
import com.nftfont.module.user.user.application.AuthService;
import com.nftfont.module.user.user.domain.UserRefreshToken;
import com.nftfont.module.user.user.domain.UserRefreshTokenRepository;
import com.nftfont.module.user.user.presentation.request.AuthReqModel;
import com.nftfont.module.user.user.presentation.response.ApiResponse;
import com.nftfont.core.oauth.entity.RoleType;
import com.nftfont.core.configuration.jwt.JwtToken;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ApiResponse login(HttpServletRequest request, HttpServletResponse response, @RequestBody AuthReqModel authReqModel){
        return authService.authLogin(authReqModel,request,response);
    }

    @GetMapping("/refresh")
    public ApiResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return authService.authRefresh(request,response);
    }

}

