package com.nftfont.module.user.user_follow.domain;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.nftfont.module.user.user_follow.domain.QUserFollow.userFollow;

@RequiredArgsConstructor
@Repository
@Slf4j
public class UserFollowRepoSupport {
    private final JPAQueryFactory queryFactory;

    public Long getFollowCount(Long userId){
        BooleanBuilder where = new BooleanBuilder();

        where.and(userFollow.toUser.id.eq(userId));

        return Long.valueOf(queryFactory
                .select(userFollow.count())
                .from(userFollow)
                .where(where)
                .fetchOne());
    }
    public Long getFollowersCount(Long userId){
        BooleanBuilder where = new BooleanBuilder();

        where.and(userFollow.fromUser.id.eq(userId));

        return Long.valueOf(queryFactory
                .select(userFollow.count())
                .from(userFollow)
                .where(where)
                .fetchOne());
    }
}
