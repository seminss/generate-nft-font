package com.nftfont.module.font.font.dto;

import com.nftfont.module.font.font.domain.QNftFont;
import com.nftfont.module.user.domain.user.QUser;
import com.nftfont.module.user.domain.userprofile.QUserProfile;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class FontThumbnailDto {
    private Long fontId;
    private String fontThumbnailImage;
    private Long likedCount;
    private Long downloadCount;
    private String fontName;
    private Long creatorId;
    private String creatorName;
    private String ttfUrl;
}
