package com.nftfont.module.user.application.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public interface UserCreation {

    @Data
    class RequestDto{

        @Email
        private String email;

        @NotNull
        private String walletAddress;

        @NotNull
        private String password;

    }

    @Data
    @Builder
    class ResponseDto{

        private Long id;


        public static ResponseDto of(Long userId){
            return ResponseDto.builder().id(userId).build();
        }
    }
}
