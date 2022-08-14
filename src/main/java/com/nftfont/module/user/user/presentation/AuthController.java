package com.nftfont.module.user.user.presentation;
import com.nftfont.core.annotation.QueryStringArgResolver;
import com.nftfont.module.user.user.application.AuthService;
import com.nftfont.module.user.user.presentation.request.AuthReqModel;
import com.nftfont.module.user.user.presentation.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


//    @PostMapping("/login")
//    public ApiResponse login(HttpServletRequest request, HttpServletResponse response, @RequestBody AuthReqModel authReqModel){
//        return authService.authLogin(authReqModel,request,response);
//    }
//
    @GetMapping("/refresh")
    public ApiResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return authService.authRefresh(request,response);
    }

    @GetMapping("/kakao")
    public ApiResponse signInWithKakao(@QueryStringArgResolver String token,HttpServletRequest request,HttpServletResponse response){
        return authService.signInWithKakao(request,response,token);
    }


}

