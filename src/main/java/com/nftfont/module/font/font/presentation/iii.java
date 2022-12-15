package com.nftfont.module.font.font.presentation;

import com.nftfont.module.font.font.domain.like.UserLikeFontV2;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class iii {

    private String address;
    private Long tokenId;
    private Long userId;

    public static iii of(UserLikeFontV2 v2){
        return iii.builder()
                .address(v2.getAddress())
                .tokenId(v2.getTokenId())
                .userId(v2.getUser().getId())
                .build();
    }
}
