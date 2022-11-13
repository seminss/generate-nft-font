package com.nftfont.module.user.domain.userprincipal;

import com.nftfont.module.user.domain.user.User;
import com.nftfont.module.user.domain.userprofile.UserProfile;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserAdapter extends UserPrincipal{
    private UserProfile userProfile;
    public UserAdapter(User user,UserProfile profile) {
        super(user);
        this.userProfile = profile;
    }
}
