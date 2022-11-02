package com.nftfont.module.user.dto;

import com.nftfont.module.user.domain.user.User;
import lombok.Builder;
import lombok.Data;

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

        private String nonce;

        public static ResponseDto of(User user){
            return ResponseDto.builder()
                    .nonce(user.getNonce())
                    .build();
        }
    }
}
