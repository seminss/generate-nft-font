package com.nftfont.module.ipfs.domain;

import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.user.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FontCIDRepository extends JpaRepository<FontCID,Long> {
    Optional<FontCID> findByUserAndFont(User user, NftFont font);
}
