package com.nftfont.common.infra.aws;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDetail {
    private String url;
    private Long fileSize;
    private FileType fileType;
}
