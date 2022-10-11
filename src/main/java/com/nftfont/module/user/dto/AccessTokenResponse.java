package com.nftfont.module.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

public class AccessTokenResponse {
    @Data
    public static class RequestDto{
        @NotNull
        private String walletAddress;
        @NotNull
        private String signature;
    }

    @Data
    @Builder
    public static class ResponseDto{
        @NotNull
        private String accessToken;
        private Long userId;
        public static ResponseDto ofSuccess(String accessToken,Long userId){
            return ResponseDto.builder()
                    .accessToken(accessToken)
                    .userId(userId)
                    .build();
        }
        public static ResponseDto ofSuccess(String accessToken){
            return ResponseDto.builder()
                    .accessToken(accessToken)
                    .build();
        }
    }
}
