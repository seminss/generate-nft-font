package com.nftfont.module.font.font.domain;

import com.nftfont.module.user.domain.user.User;
import com.nftfont.module.user.domain.userprofile.UserProfile;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "nft_font")
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
    private String fontThumbnailImage;
    private String fontUrl;
    private Long likedCount;
    private Long downloadCount;

    @Column(nullable = false)
    private LocalDateTime createDt;

    @ManyToOne(targetEntity=User.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(targetEntity=UserProfile.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id",nullable = false)
    private UserProfile userProfile;

    public static NftFont ofCreation(String fontName,String fontSymbol,User user,UserProfile userProfile,
                                     String fontUrl,String fontThumbnailImage){
        return NftFont.builder()
                .likedCount(0L)
                .downloadCount(0L)
                .fontName(fontName)
                .fontSymbol(fontSymbol)
                .fontThumbnailImage(fontThumbnailImage)
                .user(user)
                .userProfile(userProfile)
                .fontUrl(fontUrl)
                .createDt(LocalDateTime.now())
                .build();
    }

}
