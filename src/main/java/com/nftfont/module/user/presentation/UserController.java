package com.nftfont.module.user.presentation;

import com.nftfont.common.dto.ApiResult;
import com.nftfont.config.security.CurrentUser;
import com.nftfont.module.user.application.UserService;
import com.nftfont.module.user.domain.user.User;
import com.nftfont.module.user.dto.UserProfileSet;
import com.nftfont.module.user.dto.UserProfileDetail;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication.getPrincipal(),#userId)")
    @PostMapping(value = "/users/{userId}/profile",consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ApiResult<UserProfileSet.ResponseDto> setProfile(@PathVariable Long userId,
                                                            @Parameter(hidden = true) @CurrentUser User user,
                                                            @RequestPart UserProfileSet.RequestDto request,
                                                            @RequestPart(required = false) MultipartFile profileImage,
                                                            @RequestPart(required = false) MultipartFile backgroundImage){
        UserProfileSet.ResponseDto responseDto = userService.setProfile(user,request,profileImage,backgroundImage);
        ApiResult<UserProfileSet.ResponseDto> apiResult = ApiResult.success(responseDto);
        return apiResult;
    }

    @GetMapping(value="/users/{userId}/profile")
    public ApiResult<UserProfileDetail.ResponseDto> getProfile(@PathVariable Long userId) {
        UserProfileDetail.ResponseDto responseDto = userService.getProfile(userId);
        ApiResult<UserProfileDetail.ResponseDto> apiResult = ApiResult.success(responseDto);
        return apiResult;
    }

    @GetMapping("/users/{userId}/wallet")
    public ApiResult<String> getWalletAddress(@PathVariable Long userId){
        String address = userService.getUserWallet(userId);
        return ApiResult.success(address);
    }
}
