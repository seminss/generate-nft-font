package com.nftfont.module.font.font.domain;

import com.nftfont.module.user.domain.user.User;
import com.nftfont.module.user.domain.userprofile.UserProfile;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "NFT_FONT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NftFont {
    @Id
    @Column(name = "nft_font_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fontName;
    private String fontSymbol;
    @OneToOne()
    private User ownerName;

    private String fontUrl; // aws s3 파일 저장 s3의 주소
    private Long likedCount;
    private Long downloadCount;

    @Column(nullable = false)
    private LocalDateTime createDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id",nullable = false)
    private UserProfile userProfile;

    public static NftFont ofCreation(String fontName,String fontSymbol,User user,UserProfile userProfile,String fontUrl){
        return NftFont.builder()
                .fontName(fontName)
                .fontSymbol(fontSymbol)
                .user(user)
                .userProfile(userProfile)
                .fontUrl(fontUrl)
                .createDt(LocalDateTime.now())
                .build();
    }

}
