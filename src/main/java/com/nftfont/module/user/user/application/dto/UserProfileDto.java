package com.nftfont.module.user.user.application.dto;

import com.nftfont.module.user.user.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileDto {
    private Long userSeq;
    private String name;
    private String userId;
    private String profileImageUrl;
    private String backgroundImageUrl;
    private String selfDescription;

    public static UserProfileDto of(User user){
        return UserProfileDto.builder()
                .userSeq(user.getUserSeq())
                .name(user.getUsername())
                .userId(user.getUserId())
                .profileImageUrl(user.getProfileImageUrl())
                .backgroundImageUrl(user.getBackgroundImageUrl())
                .selfDescription(user.getSelfDescription())
                .build();
    }
}
