package com.nftfont.module.user.domain.userprincipal;

import com.nftfont.module.user.domain.user.User;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserAccount extends UserPrincipal{
    private User user;

    public UserAccount(User user) {
        super(user);
        this.user = user;
    }
}
