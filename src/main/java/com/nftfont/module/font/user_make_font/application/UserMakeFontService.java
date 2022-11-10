package com.nftfont.module.font.user_make_font.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nftfont.common.utils.FileUtil;
import com.nftfont.config.redis.CacheKey;
import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.font.font.domain.NftFontRepository;
import com.nftfont.module.glyph.domain.Glyph;
import com.nftfont.module.glyph.domain.GlyphRepository;

import com.nftfont.module.font.user_make_font.dto.FontCreate;
import com.nftfont.module.ipfs.IpfsService;
import com.nftfont.module.metadata.MetaDataService;
import com.nftfont.module.metadata.MetadataOfGlyph;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import storage.nft.ApiException;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserMakeFontService {

    private final IpfsService ipfsService;
    private final GlyphRepository glyphRepository;
    private final MetaDataService metaDataService;
    private final NftFontRepository nftFontRepository;
    private final RedisTemplate<String,Object> redisTemplate;
    private final ApplicationEventPublisher eventPublisher;
    private final ObjectMapper mapper;


}
