package com.nftfont.module.user.application;


import com.nftfont.common.exception.ConflictException;
import com.nftfont.common.infra.aws.S3Path;
import com.nftfont.module.file.image_file.ImageFileDto;
import com.nftfont.module.file.image_file.ImageFileService;
import com.nftfont.module.user.domain.user.User;
import com.nftfont.module.user.domain.user.UserRepository;
import com.nftfont.module.user.domain.userprofile.UserProfile;
import com.nftfont.module.user.domain.userprofile.UserProfileRepository;
import com.nftfont.module.user.dto.UserProfileSet;
import com.nftfont.module.user.dto.UserProfileDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ImageFileService imageFileService;
    public UserProfileSet.ResponseDto setProfile(User user, UserProfileSet.RequestDto request, MultipartFile profileImage,
                                                 MultipartFile backgroundImage){
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByUser(user);
        UserProfile userProfile = new UserProfile();

        if(optionalUserProfile.isPresent()){
            userProfile = optionalUserProfile.get();
            if(request.getDeleteProfile() || profileImage != null){
                imageFileService.deleteImage(userProfile.getProfileImageUrl());
            }
            if(request.getDeleteBackground() || backgroundImage != null) {
                imageFileService.deleteImage(userProfile.getBackgroundImageUrl());
            }
        }

        ImageFileDto profile = imageFileService.saveImage(profileImage,S3Path.USER_PROFILE);
        ImageFileDto background = imageFileService.saveImage(backgroundImage,S3Path.USER_BACKGROUND);

        UserProfile save = userProfileRepository.save(UserProfile.ofSet(request,profile,background,user,userProfile.getId()));

        return UserProfileSet.ResponseDto.of(save);

    }

    public UserProfileDetail.ResponseDto getProfile(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ConflictException("유저가 없어요."));
        UserProfile userProfile = userProfileRepository.findByUser(user).orElseThrow(() -> new ConflictException("유저가 없어요."));

        return UserProfileDetail.ResponseDto.of(userProfile,user);
    }
    public String getUserWallet(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ConflictException("유저가 없어요."));
        return user.getWalletAddress();
    }
    public String getUserName(User user){
        Optional<UserProfile> byUser = userProfileRepository.findByUser(user);
        if(byUser.isPresent()){
            return byUser.get().getUser() != null ? byUser.get().getUsername() : "이름 없음";
        }else{
            return "이름없음";
        }
    }
}
