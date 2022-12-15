//package com.nftfont.module.font.font.domain.like;
//
//import com.nftfont.module.font.font.domain.QNftFont;
//import com.nftfont.module.font.font.dto.UserLikeFontDto;
//import com.nftfont.module.font.font.presentation.request.GetUserLikeFontParams;
//import com.nftfont.module.user.domain.user.QUser;
//import com.nftfont.module.user.domain.user.User;
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//import static com.nftfont.module.font.font.domain.QNftFont.*;
//import static com.nftfont.module.font.font.domain.like.QUserLikeFont.userLikeFont;
//import static com.nftfont.module.user.domain.user.QUser.user;
//
//@RequiredArgsConstructor
//@Repository
//@Slf4j
//public class UserLikeFontRepoSupport {
//    private final JPAQueryFactory queryFactory;
//    public List<UserLikeFontDto> findAllByFilter(User user, GetUserLikeFontParams params){
//        BooleanBuilder where = new BooleanBuilder();
//
//        where.and(userLikeFont.user.eq(user));
//        where.and(userLikeFont.id.goe(params.getOffset()));
//
//        return queryFactory.select(UserLikeFontDto.getBean(nftFont))
//                .from(userLikeFont)
//                .leftJoin(userLikeFont.nftFont,nftFont)
//                .where(where)
//                .limit(params.getLimit())
//                .fetch();
//    }
//    public Boolean findLikeYn(Long userId,Long fontId){
//        BooleanBuilder where = new BooleanBuilder();
//        where.and(userLikeFont.user.id.eq(userId));
//        where.and(userLikeFont.nftFont.id.eq(fontId));
//
//        return queryFactory.selectFrom(userLikeFont)
//                .leftJoin(userLikeFont.nftFont,nftFont)
//                .leftJoin(userLikeFont.user,user)
//                .where(where)
//                .fetchFirst() != null;
//    }
//}
