package com.nftfont.module.user.user_follow.domain;

import com.nftfont.module.user.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserFollowRepository extends JpaRepository<UserFollow,Long> {
    Optional<UserFollow> findByFromUserAndToUser(User fromUser,User toUser);
    void deleteByFromUserAndToUser(User fromUser,User toUser);
}
