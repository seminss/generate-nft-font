package com.nftfont.module.font.font.application.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FontDownloadEvent {
    private Long fontId;
    public static FontDownloadEvent of(Long fontId){
        return FontDownloadEvent.builder()
                .fontId(fontId)
                .build();
    }
}
