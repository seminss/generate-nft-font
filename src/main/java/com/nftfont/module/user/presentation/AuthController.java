package com.nftfont.module.user.presentation;
import com.nftfont.common.dto.ApiResult;
import com.nftfont.module.user.dto.UserCreation;
import com.nftfont.module.user.application.AuthService;
import com.nftfont.module.user.dto.UserProfileCreation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/users/signup")
    public ApiResult<UserCreation.ResponseDto> signUp(@RequestBody @Valid UserCreation.RequestDto request){
        UserCreation.ResponseDto responseDto = authService.signUpWithWallet(request);
        ApiResult<UserCreation.ResponseDto> apiResult = ApiResult.success(responseDto);
        return apiResult;
    }

}

