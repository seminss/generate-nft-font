package com.nftfont.domain.user.user;


import com.nftfont.domain.userprincipal.RoleType;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
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

    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    @NotNull
    private LocalDateTime modifiedAt;

//    public static User of(SignUpBody body){
//        return User.builder()
//                .walletAddress(body.getWalletAddress())
//                .roleType(RoleType.USER)
//                .createdAt(LocalDateTime.now())
//                .modifiedAt(LocalDateTime.now())
//                .build();
//    }
}
