package com.nftfont.module.user.dto;

import com.nftfont.module.user.domain.userprofile.UserProfile;
import lombok.Builder;
import lombok.Data;


public interface UserProfileUpdate {

    @Data
    class RequestDto{

        private String username;

        private String selfDescription;

    }

    @Data
    @Builder
    class ResponseDto{
        private Long id;

        public static UserProfileUpdate.ResponseDto of(UserProfile userProfile){
            return UserProfileUpdate.ResponseDto.builder()
                    .id(userProfile.getId())
                    .build();
        }
    }
}
