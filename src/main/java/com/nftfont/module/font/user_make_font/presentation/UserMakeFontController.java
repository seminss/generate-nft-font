package com.nftfont.module.font.user_make_font.presentation;

import com.nftfont.module.font.user_make_font.application.UserMakeFontService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import storage.nft.ApiException;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserMakeFontController {

    private final UserMakeFontService userMakeFontService;

    @PostMapping(value = "/font/{userId}/create",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void createFont(@PathVariable Long userId, @RequestPart List<MultipartFile> svgFiles) throws IOException, TranscoderException, ApiException {
        for (MultipartFile svgFile : svgFiles) {
            System.out.println(svgFile+"히ㅏ하하");
        }
        userMakeFontService.createFont(svgFiles);
    }
}
