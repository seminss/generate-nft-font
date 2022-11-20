package com.nftfont.module.ipfs.application;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface IpfsService {

    void pinning(MultipartFile file,Long userId);

}
