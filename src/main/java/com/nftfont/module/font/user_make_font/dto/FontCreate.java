package com.nftfont.module.font.user_make_font.dto;

import lombok.Builder;
import lombok.Data;

public interface FontCreate {

    @Data
    @Builder
    class ResponseDto{
        private Long fontId;

        public static ResponseDto of (Long fontId){
            return ResponseDto.builder()
                    .fontId(fontId)
                    .build();
        }

    }

}
