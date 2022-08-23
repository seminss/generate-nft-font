package com.nftfont.module.notification.user_noti_read_history.presentation;

import com.nftfont.module.notification.user_noti_read_history.application.UserNotiReadHistoryService;
import com.nftfont.module.notification.user_noti_read_history.presentation.request.UserNotiReadUpdateBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserNotiReadHistoryController {
    private final UserNotiReadHistoryService userNotiReadHistoryService;

    @GetMapping("/users/{id}/notifications/unread/count")
    public Long getUnreadNotificationCount(@PathVariable Long id){
        return userNotiReadHistoryService.getUnreadNotificationCount(id);
    }

    @PostMapping("/users/{id}/notifications/read")
    public void updateUserRead(@PathVariable Long id, @RequestBody UserNotiReadUpdateBody body){
        userNotiReadHistoryService.read(id,body);
    }

}
