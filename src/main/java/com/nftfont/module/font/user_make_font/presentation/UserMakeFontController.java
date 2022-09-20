package com.nftfont.module.font.user_make_font.presentation;

import com.nftfont.common.dto.ApiResult;
import com.nftfont.module.font.user_make_font.application.UserMakeFontService;
import com.nftfont.module.font.user_make_font.dto.FontCreate;
import com.nftfont.module.ipfs.IpfsService;
import com.nftfont.module.ipfs.Progress;
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
    private final IpfsService ipfsService;
    @PostMapping(value = "/font/create",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResult<FontCreate.ResponseDto> createFont(@RequestPart Long userId, @RequestPart List<MultipartFile> svgFiles) throws IOException, TranscoderException, ApiException,
            ParseException, ExecutionException, InterruptedException {
        FontCreate.ResponseDto responseDto = userMakeFontService.createFont(userId, svgFiles);
        ApiResult<FontCreate.ResponseDto> apiResult = ApiResult.success(responseDto);
        return apiResult;
    }

    @GetMapping("/font/{fontId}/progress/{userId}")
    public ApiResult<Progress.ResponseDto> polling(@PathVariable Long userId,@PathVariable Long fontId){
        Progress.ResponseDto responseDto = ipfsService.getProgress(userId, fontId);
        ApiResult<Progress.ResponseDto> apiResult = ApiResult.success(responseDto);
        return apiResult;
    }
}
