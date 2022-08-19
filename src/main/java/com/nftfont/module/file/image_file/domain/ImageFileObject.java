package com.nftfont.module.file.image_file.domain;

import lombok.*;

import javax.persistence.*;

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
    private String createdAt;

    @Column()
    private String originalFileName;

    private Long size;

    @Column(unique = true,nullable = false)
    private String url;
}
