package com.nftfont.domain.font.font_license;

import com.nftfont.domain.font.font.NftFont;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "font_license")
public class FontLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    private NftFont nftFont;

    @Column()
    private Long fontId;

    @Column()
    private String ext;

}
