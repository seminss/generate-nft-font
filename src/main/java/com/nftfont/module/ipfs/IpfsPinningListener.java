package com.nftfont.module.ipfs;

import com.nftfont.config.redis.CacheKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class IpfsPinningListener {

    private final RedisTemplate<String,Object> redisTemplate;

    @EventListener
    public void setValue (final IpfsPinningEvent event){

        String realKey = CacheKey.IPFS_PINNING+event.getUserId().toString();

        redisTemplate.opsForList().rightPush(realKey, event.getFileName());
    }

}
