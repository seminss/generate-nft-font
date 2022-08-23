package com.nftfont.module.notification.user_noti_read_history.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserNotiReadRepository extends JpaRepository<UserNotiReadHistory,Long> {
    Optional<UserNotiReadHistory> findByUserId(Long userId);
}
