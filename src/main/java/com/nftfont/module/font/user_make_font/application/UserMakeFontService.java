package com.nftfont.module.font.user_make_font.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nftfont.common.dto.ApiResult;
import com.nftfont.common.utils.FileUtil;
import com.nftfont.domain.font.font.NftFont;
import com.nftfont.domain.font.font.NftFontRepository;
import com.nftfont.domain.glyph.Glyph;
import com.nftfont.domain.glyph.GlyphRepository;

import com.nftfont.module.font.user_make_font.dto.FontCreate;
import com.nftfont.module.ipfs.IpfsService;
import com.nftfont.module.metadata.MetaDataService;
import com.nftfont.module.metadata.MetadataOfGlyph;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderException;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher eventPublisher;
    private final ObjectMapper mapper;
    public FontCreate.ResponseDto createFont(Long userId, List<MultipartFile> svgFiles) throws IOException, TranscoderException, ApiException {

        /**
         * png 업로드*
         */
        CompletableFuture<List<String>> pngCIDs = ipfsService.store(ipfsFile(svgFiles),userId);

        /**
         * ttf 파일로 변환 후 ttf 도 pinning 한다. *
         *  *
         */
        CompletableFuture<List<String>> ttfCIDs = ipfsService.store(List.of(new File("example/NotoSansKR-Medium.otf")),userId);
//        ipfsService.store(null,null);
        /**
         * ttf file create*
         * do something*
         * 1. 폰트 아이디 만들어줘야하고*
         * 2. s3에 저장하기*
         * 3. ipfs 에 피닝하기*
         * 4. db에 cid값과 함께 저장하기*
         */
        /**
         * 더미데이터*
         */
        Long fontId = 1L;
        NftFont nftFont = null;

        /**
         * create metadata*
         */

        ttfCIDs.thenCompose(List->{
            return null;
        });

        pngCIDs.thenCompose(List -> {
            java.util.List<Glyph> glyphs = List.stream().map(Glyph::of).collect(Collectors.toList());
            setGlyphsInfo(userId,nftFont,glyphs);
            glyphRepository.saveAll(glyphs);
            List<MetadataOfGlyph> metaData = metaDataService.createMetaData(glyphs);
            for (MetadataOfGlyph metadataOfGlyph : metaData) {
                String s;
                try {
                    s = mapper.writeValueAsString(metadataOfGlyph);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                File file = new File(UUID.randomUUID()+".json");
                try {
                    writeStringToFile(s,file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    ipfsService.store(java.util.List.of(file),userId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (TranscoderException e) {
                    throw new RuntimeException(e);
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });

        return FontCreate.ResponseDto.of(fontId);
    }

    private List<File> ipfsFile(List<MultipartFile> svgFiles) throws IOException, TranscoderException {
        List<File> files = new ArrayList<>();
        for (MultipartFile multipartFile : svgFiles) {
            files.add(FileUtil.multipartToFile(multipartFile));
        }
        List<File> pngFiles = new ArrayList<>();
        for (File svgfile : files) {
            pngFiles.add(FileUtil.svg2png(svgfile));
            svgfile.delete();
        }
        return pngFiles;
    }

    public static void writeStringToFile(String str, File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(str);
        writer.close();
    }
    public static void setGlyphsInfo(Long userId,NftFont nftFont,List<Glyph> glyphs){
        for (Glyph glyph : glyphs) {
            glyph.setFont(nftFont);
            glyph.setUserId(userId);
        }
    }

}
