package com.nftfont.module.font.font.application;

import com.nftfont.common.annotation.QueryStringArgResolver;
import com.nftfont.common.exception.ConflictException;
import com.nftfont.common.infra.aws.S3Path;
import com.nftfont.module.file.FontFile.FontFileDto;
import com.nftfont.module.file.FontFile.FontFileService;
import com.nftfont.module.font.font.application.event.FontDownloadEvent;
import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.font.font.domain.NftFontRepoSupport;
import com.nftfont.module.font.font.domain.NftFontRepository;
import com.nftfont.module.font.font.domain.like.UserLikeFont;
import com.nftfont.module.font.font.domain.like.UserLikeFontRepoSupport;
import com.nftfont.module.font.font.domain.like.UserLikeFontRepository;
import com.nftfont.module.font.font.dto.FontDetailDto;
import com.nftfont.module.font.font.dto.FontThumbnailDto;
import com.nftfont.module.font.font.dto.FontUpload;
import com.nftfont.module.font.font.dto.UserLikeFontDto;
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

    public String upload(User user, MultipartFile ttfFile, FontUpload.RequestDto request){
        UserProfile userProfile = userProfileRepository.findByUser(user).orElseThrow(ConflictException::new);
        FontFileDto fontFileDto = fontFileService.saveFile(ttfFile, S3Path.NFT_FONT);
        NftFont save = nftFontRepository.save(NftFont.ofCreation(request.getFontName(),
                request.getFontSymbol(), user,userProfile, fontFileDto.getUrl()));

        pinataService.pinning(ttfFile,user,save);

        FontCID fontCID = fontCIDRepository.findByUserAndFont(user, save).orElseThrow(ConflictException::new);

        return fontCID.getCid();
    }

    public Boolean likeFont(Long fontId,User user){
        NftFont nftFont = nftFontRepository.findById(fontId).orElseThrow(ConflictException::new);
        Optional<UserLikeFont> optionalUserLikeFont = userLikeFontRepository.findByUserAndNftFont(user, nftFont);
        if(optionalUserLikeFont.isPresent()){
            userLikeFontRepository.delete(optionalUserLikeFont.get());
            nftFontRepository.minusLikeCount(fontId);
            return false;
        }
        userLikeFontRepository.save(UserLikeFont.of(user,nftFont));
        nftFontRepository.plusLikeCount(fontId);
        return true;
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

}
