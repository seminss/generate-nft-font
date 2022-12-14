package com.nftfont.module.user.application;

import com.nftfont.config.redis.CacheKey;
import com.nftfont.module.user.domain.userprincipal.UserAccount;
import com.nftfont.module.user.domain.user.User;
import com.nftfont.module.user.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Cacheable(value = CacheKey.USER,key = "#userId",unless = "#result == null ")
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new ConcurrentModificationException("asfd"));
        if (user == null) {
            throw new UsernameNotFoundException("Can not find username.");
        }
        return new UserAccount(user);
    }
}

