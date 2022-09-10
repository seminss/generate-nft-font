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

    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication,#request.getId())")
    @PostMapping(value = "/users/{userId}/profile",consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ApiResult<UserProfileCreation.ResponseDto> createProfile(@PathVariable Long userId,
                                                                    @RequestPart(value = "body") UserProfileCreation.RequestDto request,
                                                                    @RequestPart(value = "profileImage",required = false) MultipartFile profileImageFile,
                                                                    @RequestPart(value = "backgroundImage",required = false) MultipartFile backgroundImageFile){
        UserProfileCreation.ResponseDto responseDto = userService.createProfile(userId,request,profileImageFile,backgroundImageFile);
        ApiResult<UserProfileCreation.ResponseDto> apiResult = ApiResult.success(responseDto);
        return apiResult;
    }

    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication,#request.getId())")
    @PatchMapping(value = "/users/{userId}/profile",consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ApiResult<UserProfileUpdate.ResponseDto> updateProfile(@PathVariable Long userId,
                                                                  @RequestPart(value = "body") UserProfileUpdate.RequestDto request,
                                                                  @RequestPart(value = "profileImage",required = false) MultipartFile profileImageFile,
                                                                  @RequestPart(value = "backgroundImage",required = false) MultipartFile backgroundImageFile){
        UserProfileUpdate.ResponseDto responseDto = userService.updateProfile(userId, request, profileImageFile, backgroundImageFile);
        ApiResult<UserProfileUpdate.ResponseDto> apiResult = ApiResult.success(responseDto);
        return apiResult;
    }
}
