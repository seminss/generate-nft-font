package com.nftfont.module.font.font.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NftFontRepository extends JpaRepository<NftFont,Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update nft_font set liked_count = liked_count + 1 where nft_font_id=:id", nativeQuery = true)
    void plusLikeCount(@io.lettuce.core.dynamic.annotation.Param("id") Long id);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update nft_font set liked_count = liked_count - 1 where nft_font_id=:id", nativeQuery = true)
    void minusLikeCount(@io.lettuce.core.dynamic.annotation.Param("id") Long id);

}
