package com.nftfont.module.user.user.domain;


import com.nftfont.module.user.user.presentation.request.SignUpBody;
import com.nftfont.module.user.user_pricipal.RoleType;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
public class User {
    @JsonIgnore
    @Id
    @Column(name = "USER_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String userId;

    @Column(nullable = false)
    private String walletAddress;

    @Column(name = "USERNAME",length = 100)
    @NotNull
    @Size(max = 100)
    private String username;

    @JsonIgnore
    @Column(name = "PASSWORD",length = 128)
    @NotNull
    @Size(max = 128)
    private String password;

    @Column(name = "EMAIL",length = 512, unique = true)
    @NotNull
    @Size(max = 512)
    @Email(message = "이메일 형식이 아니에요!")
    private String email;

    @Column(name = "EMAIL_VERIFIED_YN",length = 1)
    @NotNull
    private Boolean emailVerifiedYn;

    @Column(name = "PROFILE_IMAGE_URL",length = 512)
    @NotNull
    @Size(max = 512)
    private String profileImageUrl;

    @Column(name = "ROLE_TYPE",length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

    @Column(name = "BACKGROUND_IMAGE_URL")
    private String backgroundImageUrl;

    @Column(name = "SELF_DESCRIPTION")
    private String selfDescription;
    @Column(name = "CREATED_AT")
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    @NotNull
    private LocalDateTime modifiedAt;


    public static User of(SignUpBody body,String profileImageUrl,String backgroundImageUrl){
        return User.builder()
                .walletAddress(body.getWalletAddress())
                .username(body.getUserName())
                .email(body.getEmail())
                .emailVerifiedYn(false)
                .profileImageUrl(profileImageUrl)
                .backgroundImageUrl(backgroundImageUrl)
                .selfDescription(body.getSelfDescription())
                .roleType(RoleType.USER)
                .password("NO_PASS")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .userId("NO_ID")
                .build();
    }
}
