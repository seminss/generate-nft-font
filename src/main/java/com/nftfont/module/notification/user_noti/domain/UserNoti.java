package com.nftfont.module.notification.user_noti.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_noti")
public class UserNoti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id",nullable = false)
    private Long userid;

    @Column()
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDt;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    private String actUrl;

    private NotiType notiType;
    public static UserNoti of(Long userId,String title,String body,String actUrl,NotiType type){
        return UserNoti.builder()
                .userid(userId)
                .createdDt(LocalDateTime.now())
                .title(title)
                .body(body)
                .notiType(type)
                .actUrl(actUrl)
                .build();
    }
}
