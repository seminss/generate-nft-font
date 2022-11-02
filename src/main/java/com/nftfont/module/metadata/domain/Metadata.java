package com.nftfont.module.metadata.domain;

import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.glyph.domain.Glyph;
import com.nftfont.module.user.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "metadata")
public class Metadata {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    private User user;

    @OneToOne()
    private NftFont nftFont;


    @OneToOne()
    private Glyph glyph;


    @Column()
    private String ext;



}
