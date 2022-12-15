package com.nftfont.module.font.font.application;

import com.nftfont.common.annotation.QueryStringArgResolver;
import com.nftfont.common.exception.ConflictException;
import com.nftfont.common.infra.aws.S3Path;
import com.nftfont.module.file.FontFile.FontFileDto;
import com.nftfont.module.file.FontFile.FontFileService;
import com.nftfont.module.file.image_file.ImageFileDto;
import com.nftfont.module.file.image_file.ImageFileService;
import com.nftfont.module.font.font.application.event.FontDownloadEvent;
import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.font.font.domain.NftFontRepoSupport;
import com.nftfont.module.font.font.domain.NftFontRepository;
import com.nftfont.module.font.font.domain.like.UserLikeFont;
import com.nftfont.module.font.font.domain.like.UserLikeFontRepoSupport;
import com.nftfont.module.font.font.domain.like.UserLikeFontRepository;
import com.nftfont.module.font.font.domain.like.UserLikeFontV2;
import com.nftfont.module.font.font.dto.*;
import com.nftfont.module.font.font.presentation.iii;
import com.nftfont.module.font.font.presentation.request.FontRequestParam;
import com.nftfont.module.font.font.presentation.request.GetUserLikeFontParams;
import com.nftfont.module.ipfs.application.IpfsService;
import com.nftfont.module.ipfs.domain.FontCID;
import com.nftfont.module.ipfs.domain.FontCIDRepository;
import com.nftfont.module.user.domain.user.User;
import com.nftfont.module.user.domain.userprofile.UserProfile;
import com.nftfont.module.user.domain.userprofile.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FontService {
    private final NftFontRepository nftFontRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserLikeFontRepository userLikeFontRepository;
    private final UserLikeFontRepoSupport userLikeFontRepoSupport;
    private final NftFontRepoSupport nftFontRepoSupport;
    private final IpfsService pinataService;
    private final FontFileService fontFileService;
    private final ImageFileService imageFileService;
    private final FontCIDRepository fontCIDRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void download(Long fontId, HttpServletResponse response){
        NftFont nftFont = nftFontRepository.findById(fontId).orElseThrow(ConflictException::new);
        try{
            File file = new File(nftFont.getFontUrl());
            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

            FileInputStream fileInputStream = new FileInputStream(file);
            OutputStream out = response.getOutputStream();

            int read = 0;
            byte[] buffer = new byte[1024];
            while ((read = fileInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        }catch (Exception e){
        }
        eventPublisher.publishEvent(FontDownloadEvent.of(fontId));
    }

    public String upload(User user, MultipartFile ttfFile,MultipartFile fontThumbnailImage, FontUpload.RequestDto request){
        UserProfile userProfile = userProfileRepository.findByUser(user).orElseThrow(ConflictException::new);
        FontFileDto fontFileDto = fontFileService.saveFile(ttfFile, S3Path.NFT_FONT);
        ImageFileDto imageFileDto = imageFileService.saveImage(fontThumbnailImage,S3Path.NFT_FONT_THUMBNAIL);
        NftFont save = nftFontRepository.save(NftFont.ofCreation(request.getFontName(),
                request.getFontSymbol(), user,userProfile, fontFileDto.getUrl(), imageFileDto.getImageUrl()));

        pinataService.pinning(ttfFile,user,save);

        FontCID fontCID = fontCIDRepository.findByUserAndFont(user, save).orElseThrow(ConflictException::new);

        return fontCID.getCid();
    }

    public void likeFont(LikeDto request, User user){
        Optional<UserLikeFontV2> optional = userLikeFontRepository.findByAddressAndTokenIdAndUser(request.getAddress(), request.getTokenId(),user);
        // 존재하면 삭제
        if(optional.isPresent()){
            userLikeFontRepository.delete(optional.get());
            return ;
        }
        // 업스면 생성
        userLikeFontRepository.save(UserLikeFontV2.ofCreation(user, request.getAddress(),request.getTokenId()));
    }

    public List<iii> getalll(){
        List<UserLikeFontV2> all = userLikeFontRepository.findAll();
        return all.stream().map(iii::of).collect(Collectors.toList());
    }



    public List<UserLikeFontDto> findAllLikeByFilter(User user, @QueryStringArgResolver GetUserLikeFontParams params){
        return userLikeFontRepoSupport.findAllByFilter(user,params);
    }


    public List<FontThumbnailDto> findFontsByFilter(FontRequestParam requestParam){
        return nftFontRepoSupport.findFontsByFilter(requestParam);
    }

    public FontDetailDto findOneById(Long fontId){
        return nftFontRepoSupport.findById(fontId);
    }

    public Boolean isUserLikeFont(Long userId,Long fontId){
        return userLikeFontRepoSupport.findLikeYn(userId,fontId);
    }
}
