package com.nftfont.module.metadata;

import com.nftfont.domain.glyph.Glyph;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MetadataOfGlyph {
    private String description;
    private String external_url;
    private String image;
    private String name;

    private List<attribute> attributes;

    @Data
    @AllArgsConstructor
    public static class attribute{
        private String trait_type;
        private String value;
    }
    public static MetadataOfGlyph of(Glyph glyph){
        return MetadataOfGlyph.builder()
                .description("마마마ㅏ")
                .name("아ㅣ름이니다.")
                .image(glyph.getCid())
                .external_url(null)
                .attributes(List.of(new attribute[]{new attribute("aa","bb"),new attribute("aa","bb")}))
                .build();
    }
}
