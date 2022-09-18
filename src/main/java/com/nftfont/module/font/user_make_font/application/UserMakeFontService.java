package com.nftfont.module.font.user_make_font.application;

import com.nftfont.common.utils.FileUtil;
import com.nftfont.module.ipfs.IpfsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import storage.nft.ApiException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserMakeFontService {

    private final IpfsService ipfsService;

    public void createFont(List<MultipartFile> svgFiles) throws IOException, TranscoderException, ApiException {
        List<File> files = new ArrayList<>();
        for (MultipartFile multipartFile : svgFiles) {
            files.add(FileUtil.multipartToFile(multipartFile));
        }
        ipfsService.store(files);
    }
}
