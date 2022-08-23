package com.nftfont.module.notification.user_noti_read_history.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserNotiReadHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column()
    private Long lastReadId;

    @Column(nullable = false)
    private LocalDateTime readDt;

    public static UserNotiReadHistory of(Long userId,Long lastReadId){
        return UserNotiReadHistory.builder()
                .userId(userId)
                .lastReadId(lastReadId)
                .createdAt(LocalDateTime.now())
                .readDt(LocalDateTime.now())
                .build();
    }
}
