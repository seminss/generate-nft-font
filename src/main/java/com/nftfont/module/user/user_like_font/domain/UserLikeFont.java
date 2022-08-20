package com.nftfont.module.user.user_like_font.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.user.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_LIKE_FONT")
public class UserLikeFont {
    @JsonIgnore
    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ", nullable = false, insertable = false, updatable = false)
    private User user;

    @Column()
    private Long userId;

    @JsonIgnore
    @ManyToOne(targetEntity = NftFont.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "NFT_FONT_ID")
    private NftFont nftFont;

    @Column()
    private Long fontId;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;


}
