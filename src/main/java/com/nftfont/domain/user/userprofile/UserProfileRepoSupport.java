package com.nftfont.domain.user.userprofile;

import com.nftfont.domain.user.user.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.nftfont.domain.font.font.QNftFont.nftFont;
import static com.nftfont.domain.user.userprofile.QUserProfile.userProfile;

@RequiredArgsConstructor
@Repository
@Slf4j
public class UserProfileRepoSupport {
    private final JPAQueryFactory queryFactory;

    public UserProfile findByUser(User user){
        BooleanBuilder where = new BooleanBuilder();

        where.and(userProfile.user.eq(user));

        return queryFactory.selectFrom(userProfile)
                .leftJoin(userProfile.nftFonts, nftFont)
                .fetchJoin()
                .distinct()
                .fetchOne();
    }

}
