package com.nftfont.module.font.font.dto;


import com.nftfont.module.font.font.domain.QNftFont;
import com.nftfont.module.font.font.domain.like.UserLikeFont;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLikeFontDto {
    private Long fontId;
    private String fontName;
    private String fontUrl;
    private String thumbnailImage;

    public static QBean<UserLikeFontDto> getBean(QNftFont font){
        return Projections.bean(UserLikeFontDto.class,
                font.id.as("fontId"),
                font.fontName.as("fontName"),
                font.fontUrl.as("fontUrl"),
                font.fontThumbnailImage.as("thumbnailImage")
                );
    }
}
