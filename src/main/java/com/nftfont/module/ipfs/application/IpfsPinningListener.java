package com.nftfont.module.ipfs.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nftfont.config.redis.CacheKey;
import com.nftfont.module.ipfs.application.IpfsPinningEvent;
import com.nftfont.module.ipfs.domain.FontCID;
import com.nftfont.module.ipfs.domain.FontCIDRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IpfsPinningListener {

    private final RedisTemplate<String,Object> redisTemplate;
    private final FontCIDRepository fontCIDRepository;
    @EventListener
    public void save(final IpfsPinningEvent event) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CustomPinataResponse response = objectMapper.readValue(event.getBody(),CustomPinataResponse.class);
        fontCIDRepository.save(FontCID.ofCreation(response.getIpfsHash(),event.getFont(),event.getUser()));
    }



}
