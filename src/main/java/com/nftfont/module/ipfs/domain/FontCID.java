package com.nftfont.module.ipfs.domain;

import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.user.domain.user.User;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "font_cid")
public class FontCID {
    @Id
    @Column(name = "font_cid_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cid;

    @OneToOne(targetEntity = NftFont.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nft_font_id",nullable = false)
    private NftFont font;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    public static FontCID ofCreation(String cid,NftFont font,User user){
        return FontCID.builder()
                .cid(cid)
                .font(font)
                .user(user)
                .build();
    }
}
