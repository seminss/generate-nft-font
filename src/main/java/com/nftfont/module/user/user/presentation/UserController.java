package com.nftfont.module.user.user.presentation;

import com.nftfont.module.user.user.application.UserService;
import com.nftfont.module.user.user.application.dto.UserProfileDto;
import com.nftfont.module.user.user.domain.user_pricipal.UserPrincipal;
import com.nftfont.module.user.user.presentation.request.ProfileUpdateBody;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "User",description = "유저에관한 프로필")
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/me/profile")
    public UserProfileDto getMyProfile(@AuthenticationPrincipal UserPrincipal principal){
        return userService.findMyProfile(principal.getUserSeq());
    }

    @GetMapping("/users/{userSeq}/profile")
    public UserProfileDto getUserProfile(@PathVariable Long userSeq){
        return userService.findOneProfile(userSeq);
    }

    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication,#userSeq)")
    @PatchMapping(value = "/users/{userSeq}/profile",
    consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public UserProfileDto updateMyProfile(@Parameter(name = "유저의 seqId", required = true) @PathVariable Long userSeq,
                                          @RequestPart(value = "body", required = false) ProfileUpdateBody body,
                                          @RequestPart(value = "profileImageFile", required = false) MultipartFile profileImageFile,
                                          @RequestPart(value = "backgroundImageFile",required = false) MultipartFile backgroundImageFile) {
        return userService.updateProfile(userSeq,body,profileImageFile,backgroundImageFile);
    }
}
