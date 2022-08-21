package com.nftfont.module.user.user.presentation;
import com.nftfont.core.annotation.QueryStringArgResolver;
import com.nftfont.module.user.user.application.AuthService;
import com.nftfont.module.user.user.presentation.request.SignInWithTokenBody;
import com.nftfont.module.user.user.presentation.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/refresh")
    public ApiResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return authService.authRefresh(request,response);
    }

    @GetMapping("/auth/kakao")
    public ApiResponse signInWithKakao(@QueryStringArgResolver String token,HttpServletRequest request,HttpServletResponse response){
        return authService.signInWithOauth2(request,response,token);
    }

    @GetMapping("/auth/google")
    public ApiResponse signInWithGoogle(@QueryStringArgResolver String token,HttpServletRequest request,HttpServletResponse response){
        return authService.signInWithOauth2(request,response,token);
    }

    @PostMapping("/auth/accessToken")
    public ApiResponse signInWithToken(@RequestBody @Valid SignInWithTokenBody body,HttpServletRequest request,HttpServletResponse response){
        return authService.signInWithAccessToken(body,request,response);
    }
}

