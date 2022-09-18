package com.nftfont.module.ipfs;

import com.nftfont.common.utils.FileUtil;
import com.nftfont.common.utils.Pair;
import com.nftfont.domain.glyph.Glyph;
import com.nftfont.domain.glyph.GlyphRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class IpfsService {

    @Value("${ipfs.nft.storage}")
    private String token;
    private final GlyphRepository glyphRepository;
    private final ApplicationEventPublisher eventPublisher;
    @Async
    public CompletableFuture<List<String>> store(List<File> files) throws IOException, TranscoderException, ApiException {

        ApiClient defaultClient = setClient();
        setBearerAuth(defaultClient);

        NftStorageApi apiInstance = new NftStorageApi(defaultClient);
        List<String> CIDs = new ArrayList<>();
        for (File file : files) {
            UploadResponse result = apiInstance.store(file);
            CIDs.add(result.getValue().getCid());
            file.delete();
        }
        return CompletableFuture.completedFuture(CIDs);
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
