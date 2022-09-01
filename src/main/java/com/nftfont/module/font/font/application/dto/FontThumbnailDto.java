package com.nftfont.module.font.font.application.dto;

import com.nftfont.module.font.user_like_font.domain.UserLikeFont;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FontThumbnailDto {
    private Long fontId;
    private String fontThumbnail;
    private Long likedCount;
    private String ownerName;
    private String fontName;

//    public static FontThumbnailDto ofLike(UserLikeFont userLikeFont){
//        return FontThumbnailDto.builder()
//                .fontId(userLikeFont.getFontId())
//                .fontName(userLikeFont.getNftFont().getFontName())
//                .fontThumbnail(userLikeFont.getNftFont().getThumbnailUrl())
//                .likedCount(userLikeFont.getNftFont().getLikedCount())
//                .ownerName(userLikeFont.getNftFont().getOwnerName())
//                .build();
//    }
}
