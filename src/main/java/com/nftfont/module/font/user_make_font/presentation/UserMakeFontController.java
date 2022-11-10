package com.nftfont.module.font.user_make_font.presentation;

import com.nftfont.common.dto.ApiResult;
import com.nftfont.module.font.user_make_font.application.UserMakeFontService;
import com.nftfont.module.font.user_make_font.dto.FontCreate;
import com.nftfont.module.ipfs.IpfsService;
import com.nftfont.module.ipfs.Progress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import storage.nft.ApiException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserMakeFontController {

    private final UserMakeFontService userMakeFontService;
    private final IpfsService ipfsService;
}
