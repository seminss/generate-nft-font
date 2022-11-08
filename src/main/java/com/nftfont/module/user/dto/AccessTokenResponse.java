package com.nftfont.module.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;


public interface AccessTokenResponse {
    @Data
    @Schema(name = "request")
    class RequestDto{
        @NotNull
        private String walletAddress;
        @NotNull
        private String signature;
    }

    @Data
    @Builder
    @Schema(name = "response")
    class ResponseDto{
        @NotNull
        private String accessToken;
        private Long userId;
        private Boolean isSignUp;
        public static ResponseDto ofSuccess(String accessToken,Long userId,Boolean isSignUp){
            return ResponseDto.builder()
                    .accessToken(accessToken)
                    .isSignUp(isSignUp)
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
