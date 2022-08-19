package com.nftfont.module.file.image_file.application;

import com.nftfont.core.infra.aws.AwsS3Service;
import com.nftfont.module.file.image_file.domain.ImageFileObjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageFileService {
    private final AwsS3Service awsS3Service;
    private final ImageFileObjectRepository imageFileObjectRepository;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ImageFileDto saveImage(MultipartFile file){

        return null;
    }
}
