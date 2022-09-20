package com.nftfont.domain.glyph;

import com.nftfont.common.utils.Pair;
import com.nftfont.domain.font.font.NftFont;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "glyph")
public class Glyph {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    private NftFont font;

    @Column
    private String cid;
    @Column
    private String name;

    private Long userId;
    public static Glyph of(String cid){
        return Glyph.builder()
                .cid(cid)
                .name(null)
                .build();
    }


}
