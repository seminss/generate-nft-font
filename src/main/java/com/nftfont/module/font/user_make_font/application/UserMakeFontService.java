package com.nftfont.module.font.user_make_font.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nftfont.module.font.font.domain.NftFontRepository;
import com.nftfont.module.glyph.domain.GlyphRepository;

import com.nftfont.module.ipfs.application.PinataService;
import com.nftfont.module.metadata.MetaDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserMakeFontService {

    private final PinataService pinataService;
    private final GlyphRepository glyphRepository;
    private final MetaDataService metaDataService;
    private final NftFontRepository nftFontRepository;
    private final RedisTemplate<String,Object> redisTemplate;
    private final ApplicationEventPublisher eventPublisher;
    private final ObjectMapper mapper;


}
