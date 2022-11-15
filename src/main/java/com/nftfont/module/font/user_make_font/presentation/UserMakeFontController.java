package com.nftfont.module.font.user_make_font.presentation;

import com.nftfont.module.font.user_make_font.application.UserMakeFontService;
import com.nftfont.module.ipfs.application.IpfsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserMakeFontController {

    private final UserMakeFontService userMakeFontService;
    private final IpfsService ipfsService;
}
