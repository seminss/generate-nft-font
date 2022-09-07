package com.nftfont.domain.metadata;

import com.nftfont.domain.font.font.NftFont;
import com.nftfont.domain.glyph.Glyph;
import com.nftfont.domain.user.user.User;
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
