package com.nftfont.module.file;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FileDetail {
    private String url;
    private Long fileSize;
}
