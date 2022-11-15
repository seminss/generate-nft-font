package com.nftfont.module.ipfs.dto;

import com.nftfont.module.font.font.domain.FontType;
import com.nftfont.module.font.font.domain.SupportType;
import com.nftfont.module.user.domain.user.User;
import com.nftfont.module.user.dto.UserLoginInfo;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

public interface TTFPinning {
    @Data
    class RequestDto{
        @NotNull
        private Long userId;
        private String fontName;
        private String fontSymbol;
        private FontType fontType;
        private SupportType supportType;
    }

    @Data
    @Builder
    class ResponseDto{

    }

}
