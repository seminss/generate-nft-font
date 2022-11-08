package com.nftfont.module.file.image_file;


import com.nftfont.common.infra.aws.AwsS3Service;
import com.nftfont.common.infra.aws.S3Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageFileService {
    private final AwsS3Service awsS3Service;

    public ImageFileDto saveImage(MultipartFile imageFile, S3Path s3Path){
        return ImageFileDto.of(awsS3Service.uploadFile(imageFile,s3Path));
    }
    public void deleteImage(String url){
        awsS3Service.deleteFile(url);
    }
}
