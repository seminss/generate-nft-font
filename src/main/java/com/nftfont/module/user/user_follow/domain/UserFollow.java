package com.nftfont.module.user.user_follow.domain;

import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.user.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_follow")
public class UserFollow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "from_user_id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User fromUser;

    @JoinColumn(name = "to_user_id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User toUser;


    public static UserFollow of(User fromUser,User toUser){
        return UserFollow.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .build();
    }
}
