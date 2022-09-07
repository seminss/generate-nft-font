package com.nftfont.domain.user.user;


import com.nftfont.module.user.presentation.request.SignUpBody;
import com.nftfont.domain.userprincipal.RoleType;
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
    @Column()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String walletAddress;

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

    @Column(name = "ROLE_TYPE",length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

    @Column(name = "CREATED_AT")
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    @NotNull
    private LocalDateTime modifiedAt;

    public static User of(SignUpBody body){
        return User.builder()
                .walletAddress(body.getWalletAddress())
                .roleType(RoleType.USER)
                .password("NO_PASS")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }
}
