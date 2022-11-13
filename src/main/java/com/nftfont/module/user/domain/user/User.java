package com.nftfont.module.user.domain.user;


import com.nftfont.module.user.domain.userprincipal.RoleType;
import com.nftfont.module.user.domain.userprofile.UserProfile;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements Serializable {
    @JsonIgnore
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_address",nullable = false)
    private String walletAddress;

    @Column(name = "role_type",length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

    @OneToOne(targetEntity = UserProfile.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id",nullable = true)
    private UserProfile userProfile;

    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "nonce")
    private String nonce;

    @Column(name = "modified_at")
    @NotNull
    private LocalDateTime modifiedAt;

    public static User ofCreate(String walletAddress,String nonce){
        return User.builder()
                .walletAddress(walletAddress)
                .nonce(nonce)
                .roleType(RoleType.USER)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

    }

}
