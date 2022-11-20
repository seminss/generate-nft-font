package com.nftfont.module.font.font.domain;

import com.nftfont.module.font.font.dto.FontThumbnailDto;
import com.nftfont.module.font.font.presentation.request.FontRequestParam;
import com.nftfont.module.user.domain.user.QUser;
import com.nftfont.module.user.domain.userprofile.QUserProfile;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.nftfont.module.font.font.domain.QNftFont.nftFont;
import static com.nftfont.module.user.domain.user.QUser.user;
import static com.nftfont.module.user.domain.userprofile.QUserProfile.userProfile;

@RequiredArgsConstructor
@Repository
@Slf4j
public class NftFontRepoSupport {
    private final JPAQueryFactory queryFactory;

    public List<FontThumbnailDto> findFontsByFilter(FontRequestParam requestParam){
        BooleanBuilder where = new BooleanBuilder();
        if(requestParam.getKeyword()!=null){
            where.and(nftFont.fontName.contains(requestParam.getKeyword()));
        }

        where.and(nftFont.id.goe(requestParam.getOffset()));

        List<NftFont> fonts = queryFactory
                .selectFrom(nftFont)
                .leftJoin(nftFont.userProfile,userProfile).fetchJoin()
                .where(where)
                .limit(requestParam.getLimit())
                .fetch();

        return fonts.stream()
                .map(f -> new FontThumbnailDto(f.getId(),f.getFontThumbnailImage(),f.getLikedCount()
                ,f.getUserProfile().getUsername(),f.getFontName())).collect(Collectors.toList());
    }

}
