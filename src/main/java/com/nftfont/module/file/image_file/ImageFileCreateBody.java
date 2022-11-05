package com.nftfont.module.file.image_file;

import lombok.Data;

@Data
public class ImageFileCreateBody {
    private Long memberId;

    private Long domainId;
    private String imageUrl;
    private String imageThumbUrl;

    public static ImageFileCreateBody of(Long memberId,
                                         Long domainId, String imageUrl, String imageThumbUrl) {
        ImageFileCreateBody body = new ImageFileCreateBody();

        body.setMemberId(memberId);

        body.setDomainId(domainId);
        body.setImageUrl(imageUrl);
        body.setImageThumbUrl(imageThumbUrl);

        return body;
    }
}
