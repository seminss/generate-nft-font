package com.nftfont.module.ipfs.application;

import org.springframework.web.multipart.MultipartFile;

public interface IpfsService {

    void asyncStore(MultipartFile file);

}
