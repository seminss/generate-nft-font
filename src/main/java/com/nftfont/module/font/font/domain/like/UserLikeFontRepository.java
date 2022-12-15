package com.nftfont.module.font.font.domain.like;

import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.user.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLikeFontRepository extends JpaRepository<UserLikeFontV2,Long> {
    Optional<UserLikeFontV2> findByAddressAndTokenIdAndUser(String address,Long tokenId,User user);
    List<UserLikeFontV2> findAllByUser(User user);
}
