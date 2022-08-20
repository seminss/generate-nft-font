package com.nftfont.module.user.user_like_font.application;

import com.nftfont.module.user.user_like_font.domain.UserLikeFont;
import com.nftfont.module.user.user_like_font.domain.UserLikeFontRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLikeFontService {
    private final UserLikeFontRepository userLikeFontRepository;

    public Optional<List<UserLikeFont>> findHistoryByUserId(Long userId){
        return userLikeFontRepository.findAllByUserId(userId);
    }

}
