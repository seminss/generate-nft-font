package com.nftfont.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserId(String userId);
    Optional<User> findById(Long id);
}
