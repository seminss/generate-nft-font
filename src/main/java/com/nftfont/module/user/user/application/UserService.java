package com.nftfont.module.user.user.application;

import com.nftfont.module.file.image_file.application.ImageFileDto;
import com.nftfont.module.file.image_file.application.ImageFileService;
import com.nftfont.module.user.user.application.dto.UserProfileDto;
import com.nftfont.module.user.user.domain.User;
import com.nftfont.module.user.user.domain.UserRepository;
import com.nftfont.module.user.user.presentation.request.ProfileUpdateBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ImageFileService imageFileService;

    public UserProfileDto findMyProfile(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new NotFoundException("마마마"));
        return UserProfileDto.of(user);
    }

    public UserProfileDto findOneProfile(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new NotFoundException("aBC"));
        return UserProfileDto.of(user);
    }

    public UserProfileDto updateProfile(Long id, ProfileUpdateBody profileUpdateBody, MultipartFile profileImageFile,MultipartFile backgroundImageFile){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("aSDFA"));
        if(profileUpdateBody == null){
            System.out.println("asdldaskk");
        }

        if(profileImageFile != null && !profileImageFile.isEmpty()){
            if(user.getProfileImageUrl() != null){
                imageFileService.deleteImageFile(user.getProfileImageUrl());
                ImageFileDto imageFileDto = imageFileService.saveProfileImage(profileImageFile);
                user.setProfileImageUrl(imageFileDto.getImageUrl());
            }
        }

        if(backgroundImageFile != null && !backgroundImageFile.isEmpty()){
            imageFileService.deleteImageFile(user.getBackgroundImageUrl());
            ImageFileDto imageFileDto = imageFileService.saveOriginalImage(backgroundImageFile);
            user.setBackgroundImageUrl(imageFileDto.getImageUrl());
        }

        if(profileUpdateBody.getName() != null){
            user.setUsername(profileUpdateBody.getName());
        }

        if(profileUpdateBody.getSelfDescription() != null){
            user.setSelfDescription(profileUpdateBody.getSelfDescription());
        }

        return UserProfileDto.of(userRepository.save(user));
    }
}
