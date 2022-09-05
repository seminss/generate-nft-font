package com.nftfont.module.user.presentation.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessSignInDto {
    private String accessToken;
    private Long userId;
    private String email;

    public static SuccessSignInDto of (String accessToken,Long userId,String email){
        return SuccessSignInDto.builder()
                .accessToken(accessToken)
                .email(email)
                .userId(userId)
                .build();
    }
}
