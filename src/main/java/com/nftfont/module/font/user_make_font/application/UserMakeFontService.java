package com.nftfont.module.font.user_make_font.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nftfont.common.utils.FileUtil;
import com.nftfont.common.utils.Pair;
import com.nftfont.domain.glyph.Glyph;
import com.nftfont.domain.glyph.GlyphRepository;
import com.nftfont.module.ipfs.IpfsService;
import com.nftfont.module.metadata.MetaDataService;
import com.nftfont.module.metadata.MetadataOfGlyph;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import storage.nft.ApiException;
import storage.nft.JSON;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserMakeFontService {

    private final IpfsService ipfsService;
    private final GlyphRepository glyphRepository;
    private final MetaDataService metaDataService;
    private final ObjectMapper mapper;
    public void createFont(Long userId,List<MultipartFile> svgFiles) throws IOException, TranscoderException, ApiException, ParseException, ExecutionException, InterruptedException {

        /**
         * png 업로드*
         */
        CompletableFuture<List<String>> pngCIDs = ipfsService.store(ipfsFile(svgFiles));

        /**
         * ttf 파일로 변환 후 ttf 도 pinning 한다. *
         *  *
         */
        CompletableFuture<List<String>> ttfCIDs = ipfsService.store(List.of(new File("example/NotoSansKR-Bold.otf")));
//        ipfsService.store(null,null);
        /**
         * ttf file create*
         * something*
         */

        /**
         * create metadata*
         */

        System.out.println("마ㅣ마ㅣ마ㅏ마마마ㅏaaaaaaaa");
        pngCIDs.thenCompose(List -> {
            java.util.List<Glyph> glyphs = List.stream().map(Glyph::of).collect(Collectors.toList());
            List<MetadataOfGlyph> metaData = metaDataService.createMetaData(glyphs);
            for (MetadataOfGlyph metadataOfGlyph : metaData) {
                String s = null;
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
                    ipfsService.store(java.util.List.of(file));
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
//        List<String> PCIDs = pngCIDs.thenCompose(s -> ttfCIDs.get());
//        String TCID = ttfCIDs.get().get(0);


        System.out.println("마ㅣ마ㅣ마ㅏ마마마ㅏ");
//
//        /**
//         * png 피닝을 바탕으로 음.. *
//         * 각 피닝마다 메타데이터 생성해줘야하는데 *
//         */
//        List<MetadataOfGlyph> metaData = metaDataService.createMetaData(collect);
//
//        // 이후 ipfs 또 피닝하기
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
}
