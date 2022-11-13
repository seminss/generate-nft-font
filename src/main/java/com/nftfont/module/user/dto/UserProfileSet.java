package com.nftfont.module.user.dto;

import com.nftfont.module.user.domain.userprofile.UserProfile;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

public interface UserProfileSet {


    @Data
    class RequestDto{
        @NotNull
        private String username;

        private String email;

        private String selfDescription;

    }


    @Data
    @Builder
    class ResponseDto{

        private Long id;

        public static ResponseDto of(UserProfile userProfile){
            return ResponseDto.builder()
                    .id(userProfile.getId())
                    .build();
        }
    }
}
