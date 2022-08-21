package com.nftfont.module.user.user_like_font.presentation;

import com.nftfont.module.user.user_like_font.application.UserLikeFontService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
@RequiredArgsConstructor
public class UserLikeFontController {
    private final UserLikeFontService userLikeFontService;

}
