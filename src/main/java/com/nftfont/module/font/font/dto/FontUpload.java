package com.nftfont.module.font.font.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public interface FontUpload {
    @Data
    class RequestDto{
        @NotNull
        private Long userId;
        private String fontName;
        private String fontSymbol;
        private String fontDescription;
    }

    @Data
    @Builder
    class ResponseDto{
        private String cid;
    }

}
