package com.nftfont.module.font.font.presentation;

import com.nftfont.common.annotation.QueryStringArgResolver;
import com.nftfont.common.dto.ApiResult;
import com.nftfont.config.security.CurrentUser;
import com.nftfont.module.font.font.application.FontService;
import com.nftfont.module.font.font.dto.FontUpload;
import com.nftfont.module.font.font.dto.LikeDto;
import com.nftfont.module.font.font.dto.UserLikeFontDto;
import com.nftfont.module.font.font.dto.vhsxmwhghl;
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
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserFontController {
    private final FontService fontService;
    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication.getPrincipal(),#user.getId())")
    @PostMapping(value ="/font/pinning",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ApiResult<String> uploadTTFFile(@Parameter(hidden = true) @CurrentUser User user,
                                           @RequestPart MultipartFile ttfFile,
                                           @RequestPart MultipartFile fontThumbnailImage,
                                           @RequestPart FontUpload.RequestDto request){
        String cid = fontService.upload(user, ttfFile,fontThumbnailImage ,request);
        ApiResult<String> success = ApiResult.success(cid);
        return success;
    }
    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication.getPrincipal(),#userId)")
    @PostMapping("/users/{userId}/like-fonts/toggle")
    public void likeFont(@RequestBody LikeDto request, @PathVariable Long userId,
                                       @Parameter(hidden = true) @CurrentUser User user){
        fontService.likeFont(request,user);
    }

    @GetMapping("/fonts/likes")
    public ApiResult<Map<String,Long>> getAll(){
        Map<String,Long> getalll = fontService.getalll();
        ApiResult<Map<String,Long>> success = ApiResult.success(getalll);
        return success;
    }

    @GetMapping("/fonts/like/all")
    public ApiResult<List<vhsxmwhghl>> getUserLikeFonts(@QueryStringArgResolver GetUserLikeFontParams params){
        List<vhsxmwhghl> allByFilter = fontService.findAllByFilter(params.getAddress());
        ApiResult<List<vhsxmwhghl>> success = ApiResult.success(allByFilter);
        return success;
    }

//    @PreAuthorize("@apiSecurityChecker.hasUserPermission(authentication.getPrincipal(),#userId)")
//    @GetMapping("/users/{userId}/like-fonts")
//    public ApiResult<List<UserLikeFontDto>> getUserLikeFonts(@Parameter(hidden = true) @CurrentUser User user, @PathVariable Long userId,
//                                                             @QueryStringArgResolver GetUserLikeFontParams params){
//        List<UserLikeFontDto> likes = fontService.findAllLikeByFilter(user, params);
//        ApiResult<List<UserLikeFontDto>> success = ApiResult.success(likes);
//        return success;
//    }

//    @GetMapping("/users/{userId}/like-fonts/likeYn")
//    public ApiResult<Boolean> isUserLikeFont(@PathVariable Long userId,@RequestParam Long fontId){
//        Boolean flag = fontService.isUserLikeFont(userId,fontId);
//        return ApiResult.success(flag);
//    }
}
