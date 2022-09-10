package com.nftfont.domain.file.image_file;

import com.nftfont.domain.file.FileDetail;
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

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

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
