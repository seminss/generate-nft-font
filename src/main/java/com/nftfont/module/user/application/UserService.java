package com.nftfont.module.user.application;


import com.nftfont.common.exception.ConflictException;
import com.nftfont.common.infra.aws.S3Path;
import com.nftfont.module.file.image_file.ImageFileDto;
import com.nftfont.module.file.image_file.ImageFileService;
import com.nftfont.module.user.domain.user.User;
import com.nftfont.module.user.domain.user.UserRepository;
import com.nftfont.module.user.domain.userprofile.UserProfile;
import com.nftfont.module.user.domain.userprofile.UserProfileRepoSupport;
import com.nftfont.module.user.domain.userprofile.UserProfileRepository;
import com.nftfont.module.user.dto.UserProfileCreation;
import com.nftfont.module.user.dto.UserProfileDetail;
import com.nftfont.module.user.dto.UserProfileUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileRepoSupport userProfileRepoSupport;
    private final ImageFileService imageFileService;
    public UserProfileCreation.ResponseDto setProfile(Long userId, UserProfileCreation.RequestDto request, MultipartFile profileImage,
                                                      MultipartFile backgroundImage){
        User user = userRepository.findById(userId).orElseThrow(()-> new ConflictException("유저가업서요"));

        ImageFileDto profile = null;
        ImageFileDto background = null;

        if(!profileImage.isEmpty()){
            profile = imageFileService.saveImage(profileImage,S3Path.USER_PROFILE);
        }
        if(!backgroundImage.isEmpty()){
            background = imageFileService.saveImage(backgroundImage,S3Path.USER_BACKGROUND);
        }

        UserProfile userProfile = UserProfile.ofCreation(request,user,profile,background);

        UserProfile save = userProfileRepository.save(userProfile);

        return UserProfileCreation.ResponseDto.of(save);

    }

    public UserProfileDetail.ResponseDto getProfile(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ConflictException("유저가 없어요."));
        UserProfile userProfile = userProfileRepository.findByUser(user).orElseThrow(() -> new ConflictException("유저가 없어요."));

        return UserProfileDetail.ResponseDto.of(userProfile);
    }
}
