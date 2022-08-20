package com.nftfont.module.user.user_follow.application;

import com.nftfont.module.user.user.domain.User;
import com.nftfont.module.user.user.domain.UserRepository;
import com.nftfont.module.user.user_follow.domain.UserFollow;
import com.nftfont.module.user.user_follow.domain.UserFollowRepoSupport;
import com.nftfont.module.user.user_follow.domain.UserFollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserFollowService {
    private final UserFollowRepository userFollowRepository;
    private final UserRepository userRepository;
    private final UserFollowRepoSupport userFollowRepoSupport;
    public void follow(Long fromUserId, Long toUserId){
        User fromUser = userRepository.findById(fromUserId).orElseThrow(()->new RuntimeException("abcc"));
        User toUser = userRepository.findById(toUserId).orElseThrow(()->new RuntimeException("abfd"));

        Optional<UserFollow> userFollow = userFollowRepository.findByFromUserAndToUser(fromUser,toUser);
        if(!userFollow.isEmpty()){
            throw new RuntimeException("akkdasf");
        }
        userFollowRepository.save(UserFollow.of(fromUser,toUser));
    }

    public void unfollow(Long fromUserId, Long toUserId){
        User fromUser = userRepository.findById(fromUserId).orElseThrow(()->new RuntimeException("abcc"));
        User toUser = userRepository.findById(toUserId).orElseThrow(()->new RuntimeException("abfd"));

        Optional<UserFollow> userFollow = userFollowRepository.findByFromUserAndToUser(fromUser,toUser);

        if(userFollow.isEmpty()){
            throw new RuntimeException("asbda");
        }
        userFollowRepository.deleteByFromUserAndToUser(fromUser,toUser);
    }

    public Long findFollowsCount(Long userId){
        return userFollowRepoSupport.getFollowCount(userId);
    }
    public Long findFollowersCount(Long userId){
        return userFollowRepoSupport.getFollowersCount(userId);
    }
}
