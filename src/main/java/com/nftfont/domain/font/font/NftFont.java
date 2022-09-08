package com.nftfont.domain.font.font;

import com.nftfont.domain.user.user.User;
import com.nftfont.domain.user.userprofile.UserProfile;
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
    private String fontUrl; // aws s3 파일 저장 s3의 주소
    //
    private Long likedCount;
    @OneToOne()
    private User producer; //테이블 조인
    private String fontName;
    private FontType fontType;
    private SupportType supportType;
    @Column(nullable = false)
    private LocalDateTime createDt;
    private Long downloadCount;

    //@ManyToOne(targetEntity = UserProfile.class)
    //@JoinColumn(name = "USER_PROFILE_ID", insertable = false, updatable = false)

}
