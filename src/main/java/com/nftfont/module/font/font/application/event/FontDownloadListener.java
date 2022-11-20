package com.nftfont.module.font.font.application.event;

import com.nftfont.module.font.font.domain.NftFontRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FontDownloadListener {
    private final NftFontRepository nftFontRepository;

    @EventListener
    public void save(final FontDownloadEvent event){
        nftFontRepository.plusDownloadCount(event.getFontId());
    }
}
