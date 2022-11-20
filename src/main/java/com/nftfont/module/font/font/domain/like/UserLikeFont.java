package com.nftfont.module.font.font.domain.like;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.user.domain.user.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "USER_LIKE_FONT")
public class UserLikeFont {
    @JsonIgnore
    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne(targetEntity = NftFont.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nft_font_id")
    private NftFont nftFont;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;


    public static UserLikeFont of(User user,NftFont font){
        return UserLikeFont.builder()
                .user(user)
                .nftFont(font)
                .createdAt(LocalDateTime.now())
                .build();

    }
}
