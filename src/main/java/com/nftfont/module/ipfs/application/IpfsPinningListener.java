package com.nftfont.module.ipfs.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nftfont.config.redis.CacheKey;
import com.nftfont.module.ipfs.application.IpfsPinningEvent;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IpfsPinningListener {

    private final RedisTemplate<String,Object> redisTemplate;

    @EventListener
    public void save(final IpfsPinningEvent event) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        System.out.println(event.getBody()+"바디입니다.");
        CustomPinataResponse response = objectMapper.readValue(event.getBody(),CustomPinataResponse.class);
        System.out.println(response.getIpfsHash()+"해쉬값");
    }



}
