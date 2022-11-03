package com.nftfont.module.user.application;


import com.nftfont.common.exception.ConflictException;
import com.nftfont.module.user.domain.user.User;
import com.nftfont.module.user.domain.user.UserRepository;
import com.nftfont.module.user.domain.userprofile.UserProfile;
import com.nftfont.module.user.domain.userprofile.UserProfileRepoSupport;
import com.nftfont.module.user.domain.userprofile.UserProfileRepository;
import com.nftfont.module.file.image_file.application.ImageFileDto;
import com.nftfont.module.file.image_file.application.ImageFileService;
import com.nftfont.module.user.dto.UserProfileCreation;
import com.nftfont.module.user.dto.UserProfileUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileRepoSupport userProfileRepoSupport;
    private final ImageFileService imageFileService;
    public UserProfileCreation.ResponseDto createProfile(Long userId,UserProfileCreation.RequestDto request){
        User user = userRepository.findById(userId).orElseThrow(()-> new ConflictException("유저가업서요"));

        UserProfile userProfile = UserProfile.ofCreation(request,user);

        UserProfile save = userProfileRepository.save(userProfile);

        return UserProfileCreation.ResponseDto.of(save);

    }

    public UserProfileUpdate.ResponseDto updateProfile(Long userId,UserProfileUpdate.RequestDto request){
        User user = userRepository.findById(userId).orElseThrow(()-> new ConflictException("유저가 없어요."));
        UserProfile userProfile = userProfileRepoSupport.findByUser(user);

        UserProfile save = userProfileRepository.save(userProfile.copyWith(request));

        return UserProfileUpdate.ResponseDto.of(save);
    }

}
