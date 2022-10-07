package com.nftfont.module.user.presentation;
import com.nftfont.common.dto.ApiResult;
import com.nftfont.module.user.dto.UserCreation;
import com.nftfont.module.user.application.AuthService;
import com.nftfont.module.user.dto.UserLoginInfo;
import com.nftfont.module.user.dto.UserProfileCreation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/users/signIn")
    public ApiResult<UserLoginInfo.ResponseDto> signIn(@RequestBody @Valid UserLoginInfo.RequestDto request){
        UserLoginInfo.ResponseDto responseDto = authService.signUpWithWallet(request);
        ApiResult<UserLoginInfo.ResponseDto> apiResult = ApiResult.success(responseDto);
        return apiResult;
    }

}

