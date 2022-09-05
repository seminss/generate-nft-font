package com.nftfont.domain.font.user_like_font;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLikeFontRepository extends JpaRepository<UserLikeFont,Long> {
    Optional<List<UserLikeFont>> findAllByUserId(Long userId);
}
