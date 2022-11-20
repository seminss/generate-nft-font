package com.nftfont.module.font.font.presentation;


import com.nftfont.common.annotation.QueryStringArgResolver;
import com.nftfont.common.dto.ApiResult;
import com.nftfont.module.font.font.application.FontService;
import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.font.font.dto.FontThumbnailDto;
import com.nftfont.module.font.font.presentation.request.FontRequestParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
public class FontController {

    private final FontService fontService;

    @GetMapping("/fonts")
    public ApiResult<List<FontThumbnailDto>> getFonts(@QueryStringArgResolver FontRequestParam requestParam){
        List<FontThumbnailDto> fontsByFilter = fontService.findFontsByFilter(requestParam);
        ApiResult<List<FontThumbnailDto>> success = ApiResult.success(fontsByFilter);
        return success;
    }
}
