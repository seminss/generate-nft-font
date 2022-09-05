package com.nftfont.module.user.presentation;

import com.nftfont.module.user.application.UserService;
import com.nftfont.module.user.presentation.request.ProfileUpdateBody;
import com.nftfont.module.user.application.dto.UserProfileDto;
import com.nftfont.domain.userprincipal.UserPrincipal;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "User",description = "유저")
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    //private final UserFollowService userFollowService;
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
        return userService.updateProfile(id,body,profileImageFile,backgroundImageFile);
    }
//    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication,#id)")
//    @GetMapping("/users/{id}/liked/fonts")
//    public List<FontThumbnailDto> getLikedFonts(@PathVariable Long id){
//        return userService.findUserLikedFonts(id);
//    }
//    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication,#id)")
//    @GetMapping("/users/{id}/followers")
//    public Long getFollowersCount(@PathVariable Long id){
//        return userFollowService.findFollowsCount(id);
//    }
//    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication,#id)")
//    @GetMapping("/users/{id}/follows")
//    public Long getFollowsCount(@PathVariable Long id){
//        return userFollowService.findFollowersCount(id);
//    }
}
