package com.nftfont.module.font.font.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NFT_FONT")
@Data
@Getter
@Setter
public class NftFont {
    @Id
    @Column(name = "NFT_FONT_ID")
    private Long id;
    private String ownerName;
    private String fontUrl;
    private String thumbnailUrl;
    private Long likedCount;
    private String fontName;
}
