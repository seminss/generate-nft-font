package com.nftfont.module.font.font.dto;

import com.nftfont.module.font.font.domain.like.UserLikeFontV2;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class vhsxmwhghl {
    private String address;
    private Long tokenId;
    public static vhsxmwhghl of(UserLikeFontV2 v2){
        return vhsxmwhghl.builder()
                .address(v2.getAddress())
                .tokenId(v2.getTokenId())
                .build();
    }
}
