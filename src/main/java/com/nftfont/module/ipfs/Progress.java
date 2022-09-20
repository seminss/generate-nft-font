package com.nftfont.module.ipfs;

import com.nftfont.module.font.user_make_font.dto.FontCreate;
import lombok.Builder;
import lombok.Data;

public interface Progress {


    @Data
    @Builder
    class ResponseDto{
        private String fileName;
        private Long pinnedFileCount;

        public static ResponseDto of(String fileName,Long pinnedFileCount){
            return ResponseDto.builder()
                    .fileName(fileName)
                    .pinnedFileCount(pinnedFileCount)
                    .build();
        }

    }

}
