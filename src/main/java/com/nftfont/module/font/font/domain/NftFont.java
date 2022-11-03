package com.nftfont.module.font.font.domain;

import com.nftfont.module.user.domain.user.User;
import com.nftfont.module.user.domain.userprofile.UserProfile;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fontName;
    private String fontSymbol;
    private FontType fontType;
    private SupportType supportType;

    @OneToOne()
    private User producer; //테이블 조인
    @OneToOne()
    private User ownerName;

    private String fontUrl; // aws s3 파일 저장 s3의 주소
    private Long likedCount;
    private Long downloadCount;

    @Column(nullable = false)
    private LocalDateTime createDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id",nullable = false)
    private UserProfile userProfile;

    //@ManyToOne(targetEntity = UserProfile.class)
    //@JoinColumn(name = "USER_PROFILE_ID", insertable = false, updatable = false)

}
