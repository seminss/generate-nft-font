package com.nftfont.module.user.application;

import com.nftfont.domain.user.UserProfile;
import com.nftfont.domain.user.UserProfileRepository;
import com.nftfont.module.file.image_file.application.ImageFileDto;
import com.nftfont.module.file.image_file.application.ImageFileService;
import com.nftfont.module.user.presentation.request.ProfileUpdateBody;
import com.nftfont.module.user.application.dto.UserProfileDto;
import com.nftfont.domain.user.User;
import com.nftfont.domain.user.UserRepository;
import com.nftfont.module.font.user_like_font.application.UserLikeFontService;
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
    private final UserProfileRepository userProfileRepository;
    private final ImageFileService imageFileService;
    private final UserLikeFontService userLikeFontService;

    public UserProfileDto findMyProfile(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new NotFoundException("마마마"));
        /**임시*/
        UserProfile profile = new UserProfile();
        return UserProfileDto.of(user,profile);
    }

    public UserProfileDto findOneProfile(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new NotFoundException("aBC"));
        UserProfile profile = new UserProfile();
        return UserProfileDto.of(user,profile);
    }

    public UserProfileDto updateProfile(Long id, ProfileUpdateBody profileUpdateBody, MultipartFile profileImageFile, MultipartFile backgroundImageFile){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("aSDFA"));
        UserProfile profile = new UserProfile();

        if(profileImageFile != null && !profileImageFile.isEmpty()){
            if(profile.getProfileImageUrl() != null){
                imageFileService.deleteImageFile(profile.getProfileImageUrl());
                ImageFileDto imageFileDto = imageFileService.saveProfileImage(profileImageFile);
                profile.setProfileImageUrl(imageFileDto.getImageUrl());
            }
        }

        if(backgroundImageFile != null && !backgroundImageFile.isEmpty()){
            imageFileService.deleteImageFile(profile.getBackgroundImageUrl());
            ImageFileDto imageFileDto = imageFileService.saveBackgroundImage(backgroundImageFile);
            profile.setBackgroundImageUrl(imageFileDto.getImageUrl());
        }

        if(profileUpdateBody.getName() != null){
            profile.setUsername(profileUpdateBody.getName());
        }

        if(profileUpdateBody.getSelfDescription() != null){
            profile.setSelfDescription(profileUpdateBody.getSelfDescription());
        }

        return UserProfileDto.of(userRepository.save(user), profile);
    }
//    public List<FontThumbnailDto> findUserLikedFonts(Long id){
//        Optional<List<UserLikeFont>> optionalFonts = userLikeFontService.findHistoryByUserId(id);
//        if(!optionalFonts.isPresent()){
//            return null;
//        }
//        List<UserLikeFont> fonts = optionalFonts.get();
//        return fonts.stream().map(userLikeFont -> FontThumbnailDto.ofLike(userLikeFont)).collect(Collectors.toList());
//    }
}
