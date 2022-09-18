package com.nftfont.module.font.user_make_font.presentation;

import com.nftfont.module.font.user_make_font.application.UserMakeFontService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderException;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import storage.nft.ApiException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserMakeFontController {

    private final UserMakeFontService userMakeFontService;

    @PostMapping(value = "/font/create",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public void createFont(@RequestPart Long userId, @RequestPart List<MultipartFile> svgFiles) throws IOException, TranscoderException, ApiException,
            ParseException, ExecutionException, InterruptedException {
        userMakeFontService.createFont(userId,svgFiles);
    }


    @GetMapping("/font/{userId}/progress")
    public void polling(@PathVariable Long userId){

    }
}
