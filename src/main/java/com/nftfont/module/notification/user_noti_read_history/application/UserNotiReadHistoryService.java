package com.nftfont.module.notification.user_noti_read_history.application;

import com.nftfont.module.notification.user_noti.domain.UserNotiRepoSupport;
import com.nftfont.module.notification.user_noti_read_history.domain.UserNotiReadHistory;
import com.nftfont.module.notification.user_noti_read_history.domain.UserNotiReadRepoSupport;
import com.nftfont.module.notification.user_noti_read_history.domain.UserNotiReadRepository;
import com.nftfont.module.notification.user_noti_read_history.presentation.request.UserNotiReadUpdateBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserNotiReadHistoryService {
    private final UserNotiReadRepository userNotiReadRepository;
    private final UserNotiRepoSupport userNotiRepoSupport;
    public Long getUnreadNotificationCount(Long userId){
        Optional<UserNotiReadHistory> optionalUserNotiReadHistory = userNotiReadRepository.findByUserId(userId);
        if(optionalUserNotiReadHistory.isEmpty()){
            return userNotiRepoSupport.getUserUnreadCount(userId,0L);
        }
        UserNotiReadHistory history = optionalUserNotiReadHistory.get();

        return userNotiRepoSupport.getUserUnreadCount(userId,history.getLastReadId());

    }


    public void read(Long userId, UserNotiReadUpdateBody userNotiReadUpdateBody){
        Optional<UserNotiReadHistory> optionalUserNotiReadHistory = userNotiReadRepository.findByUserId(userId);
        if(optionalUserNotiReadHistory.isEmpty()){
            userNotiReadRepository.save(UserNotiReadHistory.of(userId, userNotiReadUpdateBody.getLastReadId()));
            return ;
        }
        UserNotiReadHistory history = optionalUserNotiReadHistory.get();
        history.setLastReadId(userNotiReadUpdateBody.getLastReadId());
        history.setReadDt(LocalDateTime.now());
        userNotiReadRepository.save(history);
    }
}
