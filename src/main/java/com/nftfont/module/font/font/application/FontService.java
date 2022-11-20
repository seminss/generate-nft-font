package com.nftfont.module.font.font.application;

import com.nftfont.common.exception.ConflictException;
import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.font.font.domain.NftFontRepository;
import com.nftfont.module.font.font.dto.FontUpload;
import com.nftfont.module.ipfs.application.CIDResponse;
import com.nftfont.module.ipfs.application.IpfsService;
import com.nftfont.module.user.domain.user.User;
import com.nftfont.module.user.domain.userprofile.UserProfile;
import com.nftfont.module.user.domain.userprofile.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class FontService {
    private final NftFontRepository nftFontRepository;
    private final UserProfileRepository userProfileRepository;
    private final IpfsService pinataService;
    public Boolean isCreator(Long fontId,Long userId){
        NftFont nftFont = nftFontRepository.findById(fontId).
                orElseThrow(() -> new ConflictException("해당 폰트가 없습니다."));

        if(userId != nftFont.getUserProfile().getUser().getId()){
            throw new ConflictException("해당 폰트의 제작자가 아닙니다.");
        }
        return true;
    }

    public CIDResponse upload(User user, MultipartFile ttfFile, FontUpload.RequestDto request){
        pinataService.pinning(ttfFile,user.getId());







        UserProfile userProfile = userProfileRepository.findByUser(user).orElseThrow(ConflictException::new);
//        NftFont.ofCreation(request.getFontName(),request.getFontSymbol(),userProfile,)
        return null;
    }


}
