package com.nftfont.module.file.image_file.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageFileObjectRepository extends JpaRepository<ImageFileObject,Long> {
    Optional<ImageFileObject> findByUrl(String url);
}
