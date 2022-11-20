package com.nftfont.module.ipfs.application;

import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.user.domain.user.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface IpfsService {

    void pinning(MultipartFile file, User user, NftFont font);

}
