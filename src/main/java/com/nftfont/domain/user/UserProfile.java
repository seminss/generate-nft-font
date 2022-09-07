package com.nftfont.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nftfont.domain.font.font.NftFont;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserProfile {
    @JsonIgnore
    @Id
    @Column(name = "USER_PROFILE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ", nullable = false, insertable = false, updatable = false)
    private User user;

    private Long userId;

    @Column(name = "USERNAME",length = 100)
    @NotNull
    @Size(max = 100)
    private String username;

    @Column(name = "PROFILE_IMAGE_URL",length = 512)
    @NotNull
    @Size(max = 512)
    private String profileImageUrl;

    @Column(name = "BACKGROUND_IMAGE_URL")
    private String backgroundImageUrl;

    @Column(name="USER_NFT")
    @OneToMany
    @JoinColumn(name="NFT_FONT")
    private List<NftFont> nftFontList = new ArrayList<NftFont>(); //이거 왜 칼럼 추가가 안되는걸까요,,ㅠ

    @Column(name = "SELF_DESCRIPTION")
    private String selfDescription;

    @Column(name = "PROFILE_MODIFIED_AT")
    @NotNull
    private LocalDateTime modifiedAt;
}
