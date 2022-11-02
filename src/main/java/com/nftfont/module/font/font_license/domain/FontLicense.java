package com.nftfont.module.font.font_license.domain;

import com.nftfont.module.font.font.domain.NftFont;
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
    private String ext;

}
