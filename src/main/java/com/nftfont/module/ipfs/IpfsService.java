package com.nftfont.module.ipfs;

import com.nftfont.common.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import storage.nft.ApiClient;
import storage.nft.ApiException;
import storage.nft.Configuration;
import storage.nft.api.NftStorageApi;
import storage.nft.auth.HttpBearerAuth;
import storage.nft.model.UploadResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IpfsService {

    @Value("${ipfs.nft.storage}")
    private String token;


    @Async
    public void store(List<File> files) throws IOException, TranscoderException, ApiException {

        List<File> pngFiles = new ArrayList<>();
        for (File svgFiles : files) {
            pngFiles.add(FileUtil.svg2png(svgFiles));
            svgFiles.delete();
        }
        ApiClient defaultClient = setClient();
        setBearerAuth(defaultClient);

        NftStorageApi apiInstance = new NftStorageApi(defaultClient);

        for (File pngFile : pngFiles) {
            UploadResponse result = apiInstance.store(pngFile);
            System.out.println(result);
            pngFile.delete();
        }

    }

    public void pinning(){

    }

    private ApiClient setClient(){
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.nft.storage");
        return defaultClient;
    }
    private void setBearerAuth(ApiClient apiClient){
        HttpBearerAuth bearerAuth = (HttpBearerAuth) apiClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken(token);
    }

}
