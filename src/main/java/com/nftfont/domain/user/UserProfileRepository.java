package com.nftfont.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository  extends JpaRepository<User,Long> {
    UserProfile findByUserId(String userId);
    Optional<User> findById(Long id);
}
