package com.nftfont.domain.user.userprofile;

import com.nftfont.domain.user.user.User;
import com.nftfont.domain.user.userprofile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository  extends JpaRepository<UserProfile,Long> {
}
