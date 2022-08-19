package com.nftfont.module.user.user.application.dto;

import com.nftfont.module.user.user.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileDto {
    private Long id;
    private String name;
    private String profileImageUrl;
    private String backgroundImageUrl;
    private String selfDescription;

    public static UserProfileDto of(User user){
        return UserProfileDto.builder()
                .id(user.getId())
                .name(user.getUsername())
                .profileImageUrl(user.getProfileImageUrl())
                .backgroundImageUrl(user.getBackgroundImageUrl())
                .selfDescription(user.getSelfDescription())
                .build();
    }
}
