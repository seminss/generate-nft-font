package com.nftfont.module.file.image_file;

import com.nftfont.module.file.FileDetail;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ImageFileDto {
    private String imageUrl;


    public static ImageFileDto of(FileDetail fileDetail){
        return ImageFileDto.builder()
                .imageUrl(fileDetail.getUrl())
                .build();
    }
}
