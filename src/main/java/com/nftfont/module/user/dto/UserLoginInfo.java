package com.nftfont.module.user.dto;

import com.nftfont.domain.user.user.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public interface UserLoginInfo {

    @Data
    class RequestDto{
        @NotNull
        private String walletAddress;
    }

    @Data
    @Builder
    class ResponseDto{

        private Long id;

        public static ResponseDto of(User user){
            return ResponseDto.builder()
                    .id(user.getId())
                    .build();
        }
    }
}
