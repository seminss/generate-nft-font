package com.nftfont.module.file.image_file;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "domain_image")
public class ImageFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long domainId;

    @Column(nullable = false)
    private String imageUrl;

    @Column()
    private String imageThumbUrl;

    @Column()
    private Boolean primaryYn;

    @Column(nullable = false)
    private LocalDateTime createdDt;

    public static ImageFile of(ImageFileCreateBody body, Boolean primaryYn) {
        return ImageFile.builder()
                .memberId(body.getMemberId())
                .domainId(body.getDomainId())
                .imageUrl(body.getImageUrl())
                .imageThumbUrl(body.getImageThumbUrl())
                .primaryYn(primaryYn)
                .createdDt(LocalDateTime.now())
                .build();
    }
}