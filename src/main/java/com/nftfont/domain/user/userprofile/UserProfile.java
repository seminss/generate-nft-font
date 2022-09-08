package com.nftfont.domain.user.userprofile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nftfont.domain.font.font.NftFont;
import com.nftfont.domain.user.user.User;
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

    @Column(name = "USERNAME",length = 100)
    @NotNull
    @Size(max = 100)
    private String username;

    @Column(name = "EMAIL",length = 512, unique = true)
    @NotNull
    @Size(max = 512)
    @Email(message = "이메일 형식이 아니에요!")
    private String email;

    @Column(name = "PROFILE_IMAGE_URL",length = 512)
    @NotNull
    @Size(max = 512)
    private String profileImageUrl;

    @Column(name = "BACKGROUND_IMAGE_URL")
    private String backgroundImageUrl;

    @Column(name = "SELF_DESCRIPTION")
    private String selfDescription;

    @Column(name = "PROFILE_MODIFIED_AT")
    @NotNull
    private LocalDateTime modifiedAt;
}
