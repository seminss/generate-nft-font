package com.nftfont.module.font.font.presentation;


import com.nftfont.common.annotation.QueryStringArgResolver;
import com.nftfont.common.dto.ApiResult;
import com.nftfont.module.font.font.application.FontService;
import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.font.font.dto.FontDetailDto;
import com.nftfont.module.font.font.dto.FontThumbnailDto;
import com.nftfont.module.font.font.presentation.request.FontRequestParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FontController {

    private final FontService fontService;

    @GetMapping("/fonts")
    public ApiResult<List<FontThumbnailDto>> getFonts(@QueryStringArgResolver FontRequestParam requestParam){
        List<FontThumbnailDto> fontsByFilter = fontService.findFontsByFilter(requestParam);
        ApiResult<List<FontThumbnailDto>> success = ApiResult.success(fontsByFilter);
        return success;
    }
    @GetMapping("/font/{fontId}")
    public ApiResult<FontDetailDto> getFont(@PathVariable Long fontId){
        FontDetailDto fontDetailDto = fontService.findOneById(fontId);
        ApiResult<FontDetailDto> success = ApiResult.success(fontDetailDto);
        return success;
    }

    @GetMapping("/fonts/{fontId}/download")
    public void download(@PathVariable Long fontId, HttpServletResponse response) {
        fontService.download(fontId,response);
    }
}
