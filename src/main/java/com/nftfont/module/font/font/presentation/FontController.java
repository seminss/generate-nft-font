package com.nftfont.module.font.font.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /***
     * * 영문자,한글자모,특수기호(정해야함) 총 몇개????
     */
    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication,#id)")
    @PostMapping("/font/create")
    public void createFont(){
        
    }
}
