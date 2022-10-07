package com.nftfont.module.ipfs;

import com.nftfont.module.font.user_make_font.dto.FontCreate;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public interface Progress {


    @Data
    @Builder
    class ResponseDto{
        private String fileName;
        private Long pinnedFileCount;
        private Boolean finished;
        public static ResponseDto of(String fileName,Long pinnedFileCount){
            return ResponseDto.builder()
                    .fileName(fileName)
                    .pinnedFileCount(pinnedFileCount)
                    .finished(false)
                    .build();
        }
    }
    @Data
    @Builder
    class finishedDto{
        private List<String> filenames;
        private List<String> cids;

        public static finishedDto of(String filename,String cid){
            return finishedDto.builder()
                    .build();
        }

    }

}
