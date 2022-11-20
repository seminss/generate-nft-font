package com.nftfont.module.ipfs.application;

import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.user.domain.user.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IpfsPinningEvent {
    private String fileName;
    private String body;
    private User user;
    private NftFont font;
    public static IpfsPinningEvent of(String fileName,String body,User user,NftFont font){
            return IpfsPinningEvent.builder()
                    .fileName(fileName)
                    .body(body)
                    .user(user)
                    .font(font)
                    .build();
    }
}
