package com.nftfont.module.notification.user_noti_read_history.domain;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Slf4j
public class UserNotiReadRepoSupport {
    private final JPAQueryFactory queryFactory;

}
