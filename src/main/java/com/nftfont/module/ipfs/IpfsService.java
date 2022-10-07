package com.nftfont.module.ipfs;

import com.nftfont.config.redis.CacheKey;
import com.nftfont.domain.glyph.GlyphRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class IpfsService {

    @Value("${ipfs.nft.storage}")
    private String token;
    private final GlyphRepository glyphRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final RedisTemplate<String,Object> redisTemplate;
    @Async
    public CompletableFuture<List<String>> asyncStore(List<File> files, Long userId) throws IOException, TranscoderException, ApiException {

        ApiClient defaultClient = setClient();
        setBearerAuth(defaultClient);

        NftStorageApi apiInstance = new NftStorageApi(defaultClient);
        List<String> CIDs = new ArrayList<>();
        for (File file : files) {
            UploadResponse result = apiInstance.store(file);
            CIDs.add(result.getValue().getCid());
            eventPublisher.publishEvent(IpfsPinningEvent.of(file.getName(),result.getValue().getCid(),userId));
            file.delete();
        }

        return CompletableFuture.completedFuture(CIDs);
    }

    public CompletableFuture<List<String>> store(List<File> files, Long userId) throws IOException, TranscoderException, ApiException {

        ApiClient defaultClient = setClient();
        setBearerAuth(defaultClient);

        NftStorageApi apiInstance = new NftStorageApi(defaultClient);
        List<String> CIDs = new ArrayList<>();
        for (File file : files) {
            UploadResponse result = apiInstance.store(file);
            CIDs.add(result.getValue().getCid());
            eventPublisher.publishEvent(IpfsPinningEvent.of(file.getName(),result.getValue().getCid(),userId));
            file.delete();
        }

        return CompletableFuture.completedFuture(CIDs);
    }


    public Progress.ResponseDto getProgress(Long userId,Long fondId){
        String realKey = CacheKey.IPFS_PINNING+userId.toString();

        Long size = redisTemplate.opsForList().size(realKey);
        Object o = redisTemplate.opsForList().range(realKey, size - 1, size - 1).get(0);
        List<String> range = redisTemplate.opsForList().range(realKey, 0, size).stream().map(Object::toString).collect(Collectors.toList());
        if(o==null){

        }




        String cid =(String) redisTemplate.opsForList().range(realKey, size-1, size-1).get(0);

        return Progress.ResponseDto.of(cid,size);
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
