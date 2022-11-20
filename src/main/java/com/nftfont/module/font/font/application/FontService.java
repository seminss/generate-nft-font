package com.nftfont.module.font.font.application;

import com.nftfont.common.exception.ConflictException;
import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.font.font.domain.NftFontRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FontService {
    private final NftFontRepository nftFontRepository;
    public Boolean isCreator(Long fontId,Long userId){
        NftFont nftFont = nftFontRepository.findById(fontId).
                orElseThrow(() -> new ConflictException("해당 폰트가 없습니다."));

        if(userId != nftFont.getUserProfile().getUser().getId()){
            throw new ConflictException("해당 폰트의 제작자가 아닙니다.");
        }
        return true;
    }

}
