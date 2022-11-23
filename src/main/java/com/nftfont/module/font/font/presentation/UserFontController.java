package com.nftfont.module.font.font.presentation;

import com.nftfont.common.annotation.QueryStringArgResolver;
import com.nftfont.common.dto.ApiResult;
import com.nftfont.config.security.CurrentUser;
import com.nftfont.module.font.font.application.FontService;
import com.nftfont.module.font.font.dto.FontUpload;
import com.nftfont.module.font.font.dto.LikeDto;
import com.nftfont.module.font.font.dto.UserLikeFontDto;
import com.nftfont.module.font.font.presentation.request.GetUserLikeFontParams;
import com.nftfont.module.user.domain.user.User;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserFontController {
    private final FontService fontService;
    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication.getPrincipal(),#user.getId())")
    @PostMapping(value ="/font/pinning",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ApiResult<String> uploadTTFFile(@Parameter(hidden = true) @CurrentUser User user,
                                           @RequestPart MultipartFile ttfFile, @RequestPart FontUpload.RequestDto request){
        String cid = fontService.upload(user, ttfFile, request);
        ApiResult<String> success = ApiResult.success(cid);
        return success;
    }
    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication.getPrincipal(),#userId)")
    @PostMapping("/users/{userId}/like-fonts/toggle")
    public ApiResult<Boolean> likeFont(@RequestBody LikeDto request, @PathVariable Long userId,
                                       @Parameter(hidden = true) @CurrentUser User user){
        Boolean flag = fontService.likeFont(request.getFontId(), user);
        return ApiResult.success(flag);
    }

    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication.getPrincipal(),#userId)")
    @GetMapping("/users/{userId}/like-fonts")
    public ApiResult<List<UserLikeFontDto>> getUserLikeFonts(@Parameter(hidden = true) @CurrentUser User user, @PathVariable Long userId, @QueryStringArgResolver GetUserLikeFontParams params){
        List<UserLikeFontDto> likes = fontService.findAllLikeByFilter(user, params);
        ApiResult<List<UserLikeFontDto>> success = ApiResult.success(likes);
        return success;
    }

    @GetMapping("/users/{userId}/like-fonts/likeYn")
    public ApiResult<Boolean> isUserLikeFont(@PathVariable Long userId,@RequestParam Long fontId){
        Boolean flag = fontService.isUserLikeFont(userId,fontId);
        return ApiResult.success(flag);
    }
}