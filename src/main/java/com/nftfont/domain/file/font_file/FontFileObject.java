package com.nftfont.domain.file.font_file;

import com.nftfont.common.infra.aws.FileDetail;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "font_file_object")
public class FontFileObject {
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

    public static FontFileObject of(FileDetail fileDetail, String originalFileName){
        return FontFileObject.builder()
                .deleted(false)
                .originalFileName(originalFileName)
                .url(fileDetail.getUrl())
                .createdAt(LocalDateTime.now())
                .size(fileDetail.getFileSize())
                .build();
    }
}
