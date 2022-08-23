package com.nftfont.module.notification.user_noti.application.dto;

import com.nftfont.module.notification.user_noti.domain.NotiType;
import lombok.Data;

@Data
public class NotiCreateDto {
    private String title;
    private String body;
    private String actUrl;
    private NotiType type;
}
