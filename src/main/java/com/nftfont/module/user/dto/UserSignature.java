package com.nftfont.module.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

public interface UserSignature {
    @Data
    class RequestDto{
        @NotNull
        private String walletAddress;
        @NotNull
        private String signature;
    }

    @Data
    @Builder
    class ResponseDto{
        @NotNull
        private String accessToken;
        @NotNull
        private String refreshToken;
        private Long userId;
        public static ResponseDto ofSuccess(String accessToken,String refreshToken,Long userId){
            return ResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .userId(userId)
                    .build();
        }

    }
}