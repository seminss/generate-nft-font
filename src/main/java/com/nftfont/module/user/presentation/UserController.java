package com.nftfont.module.user.presentation;

import com.nftfont.common.dto.ApiResult;
import com.nftfont.module.user.application.UserService;
import com.nftfont.module.user.dto.UserProfileCreation;
import com.nftfont.module.user.dto.UserProfileDetail;
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
    @PostMapping(value = "/users/{userId}/profile",consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ApiResult<UserProfileCreation.ResponseDto> createProfile(@PathVariable Long userId,
                                                                    @RequestPart UserProfileCreation.RequestDto request,
                                                                    @RequestPart MultipartFile profileImage,
                                                                    @RequestPart MultipartFile backgroundImage){
        UserProfileCreation.ResponseDto responseDto = userService.createProfile(userId,request,profileImage,backgroundImage);
        ApiResult<UserProfileCreation.ResponseDto> apiResult = ApiResult.success(responseDto);
        return apiResult;
    }

    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication,#userId)")
    @PatchMapping(value = "/users/{userId}/profile",consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ApiResult<UserProfileUpdate.ResponseDto> updateProfile(@PathVariable Long userId,
                                                                  @RequestPart UserProfileUpdate.RequestDto request,
                                                                  @RequestPart MultipartFile profileImage,
                                                                  @RequestPart MultipartFile backgroundImage){
        UserProfileUpdate.ResponseDto responseDto = userService.updateProfile(userId, request,profileImage,backgroundImage);
        ApiResult<UserProfileUpdate.ResponseDto> apiResult = ApiResult.success(responseDto);
        return apiResult;
    }

    @GetMapping(value="/users/{userId}/profile")
    public ApiResult<UserProfileDetail.ResponseDto> getProfile(@PathVariable Long userId) {
        UserProfileDetail.ResponseDto responseDto = userService.getProfile(userId);
        ApiResult<UserProfileDetail.ResponseDto> apiResult = ApiResult.success(responseDto);
        return apiResult;
    }
}
