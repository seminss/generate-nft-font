package com.nftfont.module.user.presentation;
import com.nftfont.common.dto.ApiResult;
import com.nftfont.module.user.application.dto.UserCreation;
import com.nftfont.module.user.presentation.request.SignUpBody;
import com.nftfont.module.user.presentation.response.ApiResponse;
import com.nftfont.module.user.application.AuthService;
import com.nftfont.module.user.presentation.request.SignInWithTokenBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

//    @GetMapping("/refresh")
//    public ApiResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
//        return authService.authRefresh(request,response);
//    }
//
//    @PostMapping("/auth/accessToken")
//    public ApiResult<UserCreation.ResponseDto> signInWithToken(@RequestBody @Valid SignInWithTokenBody body, HttpServletRequest request, HttpServletResponse response){
//        return ApiResult.success(authService.signInWithAccessToken(body,request,response));
//    }


    @PostMapping(value = "/signup",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public void signUp(@RequestPart(required = true) SignUpBody body,
                       @RequestPart(required = false) MultipartFile profileImage,
                       @RequestPart(required = false) MultipartFile backgroundImage){
        authService.signUpWithWallet(body,profileImage,backgroundImage);
    }
}

