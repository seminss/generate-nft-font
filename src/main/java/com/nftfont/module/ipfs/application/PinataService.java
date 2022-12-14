package com.nftfont.module.ipfs.application;

import com.nftfont.common.utils.FileUtil;
import com.nftfont.config.properties.PinataProperties;
import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.user.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pinata.Pinata;
import pinata.PinataException;
import pinata.PinataResponse;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class PinataService implements IpfsService{

    @Value("${ipfs.nft.storage}")
    private String token;
    private final ApplicationEventPublisher eventPublisher;
    private final RedisTemplate<String,Object> redisTemplate;
    private final PinataProperties pinataProperties;

    public void pinning(MultipartFile file, User user, NftFont font) {
        Pinata pinata = new Pinata(pinataProperties.getKey(), pinataProperties.getApiKey());
        PinataResponse pinataResponse;
        File file2 = null;
        try {
            file2 = FileUtil.multipartToFile(file);
            pinataResponse = pinata.pinFileToIpfs(file2);
            file2.delete();
        } catch (PinataException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            file2.delete();
        }
        eventPublisher.publishEvent(IpfsPinningEvent.of(file.getOriginalFilename(),pinataResponse.getBody(),user,font));
    }

//    @Async
//    public CompletableFuture<List<String>> asyncStore(List<File> files, Long userId) throws IOException,  ApiException {
//
//        ApiClient defaultClient = setClient();
//        setBearerAuth(defaultClient);
//
//        NftStorageApi apiInstance = new NftStorageApi(defaultClient);
//        List<String> CIDs = new ArrayList<>();
//        for (File file : files) {
//            UploadResponse result = apiInstance.store(file);
//            CIDs.add(result.getValue().getCid());
//            eventPublisher.publishEvent(IpfsPinningEvent.of(file.getName(),result.getValue().getCid(),userId));
//            file.delete();
//        }
//
//        return CompletableFuture.completedFuture(CIDs);
//    }
////
//    public CompletableFuture<List<String>> store(List<File> files, Long userId) throws IOException,  ApiException {
//
//        ApiClient defaultClient = setClient();
//        setBearerAuth(defaultClient);
//
//        NftStorageApi apiInstance = new NftStorageApi(defaultClient);
//        List<String> CIDs = new ArrayList<>();
//        for (File file : files) {
//            UploadResponse result = apiInstance.store(file);
//            CIDs.add(result.getValue().getCid());
//            eventPublisher.publishEvent(IpfsPinningEvent.of(file.getName(),result.getValue().getCid(),userId));
//            file.delete();
//        }
//
//        return CompletableFuture.completedFuture(CIDs);
//    }
//
//
//    public Progress.ResponseDto getProgress(Long userId, Long fondId){
//        String realKey = CacheKey.IPFS_PINNING+userId.toString();
//
//        Long size = redisTemplate.opsForList().size(realKey);
//        Object o = redisTemplate.opsForList().range(realKey, size - 1, size - 1).get(0);
//        List<String> range = redisTemplate.opsForList().range(realKey, 0, size).stream().map(Object::toString).collect(Collectors.toList());
//        if(o==null){
//
//        }
//
//
//
//
//        String cid =(String) redisTemplate.opsForList().range(realKey, size-1, size-1).get(0);
//
//        return Progress.ResponseDto.of(cid,size);
//    }
//
//    private ApiClient setClient(){
//        ApiClient defaultClient = Configuration.getDefaultApiClient();
//        defaultClient.setBasePath("https://api.nft.storage");
//        return defaultClient;
//    }
//    private void setBearerAuth(ApiClient apiClient){
//        HttpBearerAuth bearerAuth = (HttpBearerAuth) apiClient.getAuthentication("bearerAuth");
//        bearerAuth.setBearerToken(token);
//    }

}
