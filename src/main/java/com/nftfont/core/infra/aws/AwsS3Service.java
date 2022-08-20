package com.nftfont.core.infra.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.url}")
    private String baseUrl;

    public FileDetail uploadProfileImage(MultipartFile file){
        String fileName = createFileName(file.getOriginalFilename());
        String uploadFileName = "user/"+"profile/"+"profile_image/"+fileName;

        upload(file,uploadFileName);
        
        /*
         image resize 필요
         */
        
        return FileDetail.builder()
                .url(baseUrl+"/"+uploadFileName)
                .fileSize(file.getSize())
                .fileType(FileType.IMAGE_FILE)
                .build();
    }

    public FileDetail uploadOriginalImage(MultipartFile file){
        String fileName = createFileName(file.getOriginalFilename());
        String uploadFileName = "user/"+"profile/"+"background/"+fileName;

        upload(file,uploadFileName);
        return FileDetail.builder()
                .url(baseUrl+"/"+uploadFileName)
                .fileSize(file.getSize())
                .fileType(FileType.IMAGE_FILE)
                .build();
    }

    public void deleteFile(String url){
        amazonS3.deleteObject(new DeleteObjectRequest(bucket,url));
    }

    private void upload(MultipartFile file, String fileName){

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try(InputStream inputStream = file.getInputStream()){
            amazonS3.putObject(new PutObjectRequest(bucket,fileName,inputStream,objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String createFileName(String fileName){
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }
    private String getFileExtension(String fileName){
        try{
            return fileName.substring(fileName.lastIndexOf("."));
        }catch (StringIndexOutOfBoundsException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }
}
