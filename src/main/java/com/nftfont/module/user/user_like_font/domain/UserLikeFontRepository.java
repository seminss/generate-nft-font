package com.nftfont.module.user.user_like_font.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLikeFontRepository extends JpaRepository<UserLikeFont,Long> {
    Optional<List<UserLikeFont>> findAllByUserId(Long userId);
}
