package com.nftfont.module.user.presentation;

import com.nftfont.common.dto.ApiResult;
import com.nftfont.module.user.application.UserService;
import com.nftfont.module.user.dto.UserProfileCreation;
import com.nftfont.module.user.dto.UserProfileUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication,#userId)")
    @PostMapping(value = "/users/{userId}/profile")
    public ApiResult<UserProfileCreation.ResponseDto> createProfile(@PathVariable Long userId,
                                                                    @RequestBody UserProfileCreation.RequestDto request){
        UserProfileCreation.ResponseDto responseDto = userService.createProfile(userId,request);
        ApiResult<UserProfileCreation.ResponseDto> apiResult = ApiResult.success(responseDto);
        return apiResult;
    }

    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication,#userId)")
    @PatchMapping(value = "/users/{userId}/profile")
    public ApiResult<UserProfileUpdate.ResponseDto> updateProfile(@PathVariable Long userId,
                                                                  @RequestBody UserProfileUpdate.RequestDto request){
        UserProfileUpdate.ResponseDto responseDto = userService.updateProfile(userId, request);
        ApiResult<UserProfileUpdate.ResponseDto> apiResult = ApiResult.success(responseDto);
        return apiResult;
    }
}
