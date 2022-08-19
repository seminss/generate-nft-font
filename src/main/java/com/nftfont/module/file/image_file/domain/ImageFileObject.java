package com.nftfont.module.file.image_file.domain;

import com.nftfont.core.infra.aws.FileDetail;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image_file_object")
public class ImageFileObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private Boolean deleted;

    @Column()
    private LocalDateTime createdAt;

    @Column()
    private String originalFileName;

    private Long size;

    @Column(unique = true,nullable = false)
    private String url;

    public static ImageFileObject of(FileDetail fileDetail,String originalFileName){
        return ImageFileObject.builder()
                .deleted(false)
                .originalFileName(originalFileName)
                .url(fileDetail.getUrl())
                .createdAt(LocalDateTime.now())
                .size(fileDetail.getFileSize())
                .build();
    }
}
