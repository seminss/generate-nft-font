package com.nftfont.module.file.FontFile;

import com.nftfont.common.infra.aws.AwsS3Service;
import com.nftfont.common.infra.aws.S3Path;
import com.nftfont.module.file.image_file.ImageFileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class FontFileService {
    private final AwsS3Service awsS3Service;

    public FontFileDto saveFile(MultipartFile ttfFile, S3Path s3Path){
        return FontFileDto.of(awsS3Service.uploadFile(ttfFile,s3Path));
    }
}
