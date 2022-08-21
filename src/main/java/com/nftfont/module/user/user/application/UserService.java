package com.nftfont.module.user.user.application;

import com.nftfont.module.file.image_file.application.ImageFileDto;
import com.nftfont.module.file.image_file.application.ImageFileService;
import com.nftfont.module.font.font.application.dto.FontThumbnailDto;
import com.nftfont.module.user.user.application.dto.UserProfileDto;
import com.nftfont.module.user.user.domain.User;
import com.nftfont.module.user.user.domain.UserRepository;
import com.nftfont.module.user.user.presentation.request.ProfileUpdateBody;
import com.nftfont.module.user.user_like_font.application.UserLikeFontService;
import com.nftfont.module.user.user_like_font.domain.UserLikeFont;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ImageFileService imageFileService;
    private final UserLikeFontService userLikeFontService;

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
    public List<FontThumbnailDto> findUserLikedFonts(Long id){
        Optional<List<UserLikeFont>> optionalFonts = userLikeFontService.findHistoryByUserId(id);
        if(!optionalFonts.isPresent()){
            return null;
        }
        List<UserLikeFont> fonts = optionalFonts.get();
        return fonts.stream().map(userLikeFont -> FontThumbnailDto.ofLike(userLikeFont)).collect(Collectors.toList());
    }
}
