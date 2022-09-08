package com.nftfont.domain.glyph;

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
    @Column()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    @OneToOne()
    private NftFont font;
}
