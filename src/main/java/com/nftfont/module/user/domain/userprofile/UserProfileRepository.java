package com.nftfont.module.user.domain.userprofile;

import com.nftfont.module.user.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository  extends JpaRepository<UserProfile,Long> {
    Optional<UserProfile> findByUser(User user);
}
