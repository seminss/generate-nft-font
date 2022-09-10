package com.nftfont.module.file.image_file.application;

import com.nftfont.common.infra.aws.AwsS3Service;
import com.nftfont.domain.file.FileDetail;
import com.nftfont.domain.file.FilePath;
import com.nftfont.domain.file.FileType;
import com.nftfont.domain.file.image_file.ImageFileObject;
import com.nftfont.domain.file.image_file.ImageFileObjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageFileService {
    private final AwsS3Service awsS3Service;
    private final ImageFileObjectRepository imageFileObjectRepository;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ImageFileDto saveProfileImage(MultipartFile file){
        FileDetail fileDetail = awsS3Service.uploadFile(file, FileType.IMAGE_FILE, FilePath.PROFILE);

        imageFileObjectRepository.save(ImageFileObject.of(fileDetail,file.getOriginalFilename()));

        return ImageFileDto.builder()
                .imageUrl(fileDetail.getUrl())
                .build();
    }
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ImageFileDto saveBackgroundImage(MultipartFile file){
        FileDetail fileDetail = awsS3Service.uploadFile(file,FileType.IMAGE_FILE,FilePath.BACKGROUND);

        imageFileObjectRepository.save(ImageFileObject.of(fileDetail,file.getOriginalFilename()));

        return ImageFileDto.builder()
                .imageUrl(fileDetail.getUrl())
                .build();
    }
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void deleteImageFile(String url){
        Optional<ImageFileObject>  optionalImageFileObject = imageFileObjectRepository.findByUrl(url);
        if(optionalImageFileObject.isEmpty()){
            return ;
        }

        ImageFileObject imageFileObject = optionalImageFileObject.get();
        awsS3Service.deleteFile(imageFileObject.getUrl());

        imageFileObject.setDeleted(true);
        imageFileObjectRepository.save(imageFileObject);
    }
}
