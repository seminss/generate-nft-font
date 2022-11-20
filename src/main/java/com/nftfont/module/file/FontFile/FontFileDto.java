package com.nftfont.module.file.FontFile;

import com.nftfont.module.file.FileDetail;
import com.nftfont.module.file.image_file.ImageFileDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FontFileDto {
    private String url;

    public static FontFileDto of(FileDetail fileDetail){
        return FontFileDto.builder()
                .url(fileDetail.getUrl())
                .build();
    }

}
