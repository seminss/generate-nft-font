package com.nftfont.module.font.font.domain.like;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nftfont.module.user.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "USER_LIKE_FONT_V2")
public class UserLikeFontV2 {
    @JsonIgnore
    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column()
    private String address;

    @Column()
    private Long tokenId;

    public static UserLikeFontV2 ofCreation(User user,String address,Long tokenId){
        return UserLikeFontV2.builder()
                .user(user)
                .address(address)
                .tokenId(tokenId)
                .build();
    }


}
