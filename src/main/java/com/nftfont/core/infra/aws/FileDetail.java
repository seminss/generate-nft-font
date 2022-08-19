package com.nftfont.core.infra.aws;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDetail {
    private String url;
    private Long fileSize;
    private FileType fileType;
}
