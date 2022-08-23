package com.nftfont.module.notification.user_noti.application;

import com.nftfont.module.notification.user_noti.application.dto.NotiCreateDto;
import com.nftfont.module.notification.user_noti.domain.UserNoti;
import com.nftfont.module.notification.user_noti.domain.UserNotiRepoSupport;
import com.nftfont.module.notification.user_noti.domain.UserNotiRepository;
import com.nftfont.module.notification.user_noti.presentation.request.UserNotiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserNotiService {
    private final UserNotiRepoSupport userNotiRepoSupport;
    private final UserNotiRepository userNotiRepository;
    public List<UserNoti> findAllByFilter(Long id, UserNotiParam userNotiParam){
        return userNotiRepoSupport.findAllByFilter(id,userNotiParam);
    }

    public void createNotification(Long userId, NotiCreateDto notiCreateDto){
        userNotiRepository.save(UserNoti.of(userId, notiCreateDto.getTitle(),
                notiCreateDto.getBody(), notiCreateDto.getActUrl(),notiCreateDto.getType()));
    }

}
