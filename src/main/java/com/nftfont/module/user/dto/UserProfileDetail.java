package com.nftfont.module.user.dto;

import com.nftfont.module.user.domain.user.User;
import com.nftfont.module.user.domain.userprofile.UserProfile;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public interface UserProfileDetail {
    @Data
    @Builder
    class ResponseDto{
        private String username;
        private String email;
        private String profileImageUrl;
        private String backgroundImageUrl;
        private String selfDescription;
        private String walletAddress;
        public static ResponseDto of(UserProfile userProfile, User user){
            return UserProfileDetail.ResponseDto.builder()
                    .username(userProfile.getUsername())
                    .email(userProfile.getEmail())
                    .profileImageUrl(userProfile.getProfileImageUrl())
                    .backgroundImageUrl(userProfile.getBackgroundImageUrl())
                    .selfDescription(userProfile.getSelfDescription())
                    .walletAddress(user.getWalletAddress())
                    .build();
        }
    }
}
