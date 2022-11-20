package com.nftfont.module.font.font_license.application;

import com.nftfont.common.authorization.FontCreator;
import com.nftfont.common.exception.ConflictException;
import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.font.font.domain.NftFontRepository;
import com.nftfont.module.font.font_license.UpdateLicense;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FontLicenseService {
    private final NftFontRepository nftFontRepository;


    @FontCreator
    public void updateLicense(Long userId, Long fontId, UpdateLicense request){

    }
}
