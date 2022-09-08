package com.nftfont.module.font.user_like_font.application;

import com.nftfont.domain.font.user_like_font.UserLikeFont;
import com.nftfont.domain.font.user_like_font.UserLikeFontRepository;
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