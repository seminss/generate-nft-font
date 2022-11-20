package com.nftfont.module.ipfs.application;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IpfsPinningEvent {
    private String fileName;
    private String body;
    private Long userId;
    public static IpfsPinningEvent of(String fileName,String body,Long userId){
            return IpfsPinningEvent.builder()
                    .fileName(fileName)
                    .body(body)
                    .userId(userId)
                    .build();
    }
}
