package com.nftfont.module.font.font.presentation;

import com.nftfont.module.font.font.application.FontService;
import com.nftfont.module.user.user.domain.user_pricipal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FontController {
//    private final FontService fontService;
//
//    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication,#id)")
//    @PostMapping(value = "/font/upload",
//            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
//    public void uploadFont(@AuthenticationPrincipal UserPrincipal userPrincipal,
//                           @RequestPart(required = false) MultipartFile fontThumbnailImage,
//                           @RequestPart(required = false) MultipartFile fontFile){
//
//    }
}
