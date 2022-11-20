package com.nftfont.module.font.font.presentation.request;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class FontRequestParam {
    private String keyword;

    private Long offset;

    private Long limit;
}
