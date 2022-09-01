package com.nftfont.module.font.font.domain;

import com.nftfont.module.user.user.domain.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private String fontPreview;
    private Long likedCount;
    @OneToOne()
    private User producer;
    private String fontName;
    private FontType fontType;
    private SupportType supportType;
    @Column(nullable = false)
    private LocalDateTime createDt;
    private Long viewCount;
}
