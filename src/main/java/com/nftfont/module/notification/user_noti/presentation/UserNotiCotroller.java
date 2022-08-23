package com.nftfont.module.notification.user_noti.presentation;

import com.nftfont.core.annotation.QueryStringArgResolver;
import com.nftfont.module.notification.user_noti.application.UserNotiService;
import com.nftfont.module.notification.user_noti.domain.UserNoti;
import com.nftfont.module.notification.user_noti.presentation.request.UserNotiParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "UserNotiHistory",description = "유저알람")
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserNotiCotroller {
    private final UserNotiService userNotiService;

    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication,#id)")
    @GetMapping("/users/{id}/notifications")
    public List<UserNoti> getNotifications(@PathVariable Long id, @QueryStringArgResolver UserNotiParam userNotiParam){
        return userNotiService.findAllByFilter(id,userNotiParam);
    }




}
