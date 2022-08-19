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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "User",description = "유저")
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/me/profile")
    public UserProfileDto getMyProfile(@AuthenticationPrincipal UserPrincipal principal){
        if(principal == null){
            throw new RuntimeException("asd");
        }
        return userService.findMyProfile(principal.getId());
    }

    @GetMapping("/users/{id}/profile")
    public UserProfileDto getUserProfile(@PathVariable Long id){
        return userService.findOneProfile(id);
    }

    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication,#id)")
    @PatchMapping(value = "/users/{id}/profile",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public UserProfileDto updateMyProfile(@PathVariable Long id,
                                          @RequestPart(required = false) ProfileUpdateBody body,
                                          @RequestPart(required = false) MultipartFile profileImageFile,
                                          @RequestPart(required = false) MultipartFile backgroundImageFile) {
        System.out.println("여기옴");
        return userService.updateProfile(id,body,profileImageFile,backgroundImageFile);
    }
}
