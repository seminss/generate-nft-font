package com.nftfont.module.notification.user_noti.domain;

import com.nftfont.module.notification.user_noti.presentation.request.UserNotiParam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nftfont.module.notification.user_noti.domain.QUserNoti.userNoti;

@RequiredArgsConstructor
@Repository
@Slf4j
public class UserNotiRepoSupport {
    private final JPAQueryFactory queryFactory;


    public Long getUserUnreadCount(Long userId,Long lastReadId){
        BooleanBuilder where = new BooleanBuilder();

        where.and(userNoti.userid.eq(userId));
        where.and(userNoti.id.gt(lastReadId));

        return queryFactory
                .select(userNoti.count())
                .from(userNoti)
                .where(where)
                .fetchOne();
    }
    public List<UserNoti> findAllByFilter(Long userId, UserNotiParam userNotiParam){
        BooleanBuilder where = new BooleanBuilder();

        where.and(userNoti.userid.eq(userId));

        return queryFactory
                .selectFrom(userNoti)
                .where(where)
                .orderBy(userNoti.id.desc())
                .limit(userNotiParam.getLimit())
                .fetch();
    }
}
