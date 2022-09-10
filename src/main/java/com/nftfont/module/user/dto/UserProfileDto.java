package com.nftfont.module.user.dto;

import com.nftfont.domain.user.user.User;
import com.nftfont.domain.user.userprofile.UserProfile;
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

    public static UserProfileDto of(User user, UserProfile profile){
        return UserProfileDto.builder()
                .id(user.getId())
                .name(profile.getUsername())
                .profileImageUrl(profile.getProfileImageUrl())
                .backgroundImageUrl(profile.getBackgroundImageUrl())
                .selfDescription(profile.getSelfDescription())
                .build();
    }
}
