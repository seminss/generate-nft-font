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
    }
}
