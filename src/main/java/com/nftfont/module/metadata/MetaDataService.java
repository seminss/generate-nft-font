package com.nftfont.module.metadata;

import com.nftfont.domain.glyph.Glyph;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetaDataService {

    public List<MetadataOfGlyph> createMetaData(List<Glyph> collect){
        List<MetadataOfGlyph> glyphs = new ArrayList<>();
        for (Glyph glyph : collect) {
            glyphs.add(MetadataOfGlyph.of(glyph));
        }
        return glyphs;
    }

}
