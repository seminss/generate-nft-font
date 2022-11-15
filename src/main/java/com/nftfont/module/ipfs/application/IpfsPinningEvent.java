package com.nftfont.module.ipfs.application;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IpfsPinningEvent {
    private String fileName;
    private String cid;
    private Long userId;
    public static IpfsPinningEvent of(String fileName,String cid,Long userId){
            return IpfsPinningEvent.builder()
                    .fileName(fileName)
                    .cid(cid)
                    .userId(userId)
                    .build();
    }
}
