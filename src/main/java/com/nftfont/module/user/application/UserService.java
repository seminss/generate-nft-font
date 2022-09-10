package com.nftfont.module.user.application;


import com.nftfont.common.exception.ConflictException;
import com.nftfont.domain.user.user.User;
import com.nftfont.domain.user.user.UserRepository;
import com.nftfont.domain.user.userprofile.UserProfile;
import com.nftfont.domain.user.userprofile.UserProfileRepoSupport;
import com.nftfont.domain.user.userprofile.UserProfileRepository;
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
    public UserProfileCreation.ResponseDto createProfile(Long userId,UserProfileCreation.RequestDto request,MultipartFile image,MultipartFile background){
        User user = userRepository.findById(userId).orElseThrow(()-> new ConflictException("유저가업서요"));

        ImageFileDto profileImage = imageFileService.saveProfileImage(image);
        ImageFileDto backgroundImage = imageFileService.saveBackgroundImage(background);

        UserProfile userProfile = UserProfile.ofCreation(request,profileImage,backgroundImage);

        userProfile.setUser(user);

        UserProfile save = userProfileRepository.save(userProfile);

        return UserProfileCreation.ResponseDto.of(save);

    }

    public UserProfileUpdate.ResponseDto updateProfile(Long userId,UserProfileUpdate.RequestDto request,MultipartFile image,MultipartFile background){
        User user = userRepository.findById(userId).orElseThrow(()-> new ConflictException("유저가 없어요."));
        UserProfile userProfile = userProfileRepoSupport.findByUser(user);

        if(userProfile == null){
            throw new ConflictException("해당유저정보가없어요");
        }

        if(request.getUsername() != null){
            userProfile.setUsername(request.getUsername());
        }

        if(request.getSelfDescription() != null){
            userProfile.setSelfDescription(request.getSelfDescription());
        }

        if(image != null){
            if(userProfile.getProfileImageUrl() != null){
                imageFileService.deleteImageFile(userProfile.getProfileImageUrl());
            }
            ImageFileDto imageFileDto = imageFileService.saveProfileImage(image);
            userProfile.setProfileImageUrl(imageFileDto.getImageUrl());
        }

        if(image != null){
            if(userProfile.getBackgroundImageUrl() != null){
                imageFileService.deleteImageFile(userProfile.getBackgroundImageUrl());
            }
            ImageFileDto imageFileDto = imageFileService.saveBackgroundImage(background);
            userProfile.setBackgroundImageUrl(imageFileDto.getImageUrl());
        }

        UserProfile save = userProfileRepository.save(userProfile);

        return UserProfileUpdate.ResponseDto.of(save);
    }

}
