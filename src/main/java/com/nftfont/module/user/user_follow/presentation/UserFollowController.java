package com.nftfont.module.user.user_follow.presentation;

import com.nftfont.module.user.user.domain.user_pricipal.UserPrincipal;
import com.nftfont.module.user.user_follow.application.UserFollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserFollowController {
    private final UserFollowService userFollowService;
    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication,#userPrincipal.getId())")
    @PostMapping("/user/follow/{toUserId}")
    public void followUser(@PathVariable Long toUserId, @AuthenticationPrincipal UserPrincipal userPrincipal){
        if(userPrincipal == null){
            throw new RuntimeException("abcd");
        }
        userFollowService.follow(userPrincipal.getId(),toUserId);
    }
    @PostMapping("/user/unfollow/{toUserId}")
    public void unfollowUser(@PathVariable Long toUserId,@AuthenticationPrincipal UserPrincipal userPrincipal){
        if(userPrincipal == null){
            throw new RuntimeException("bcad");
        }
        userFollowService.unfollow(userPrincipal.getId(),toUserId);
    }


}
