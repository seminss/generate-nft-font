package com.nftfont.module.user.user.application;

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

    public UserProfileDto findMyProfile(Long userSeq){
        User user = userRepository.findByUserSeq(userSeq).orElseThrow(()->new NotFoundException("마마마"));
        return UserProfileDto.of(user);
    }

    public UserProfileDto findOneProfile(Long userSeq){
        User user = userRepository.findByUserSeq(userSeq).orElseThrow(()->new NotFoundException("aBC"));
        return UserProfileDto.of(user);
    }

    public UserProfileDto updateProfile(Long userSeq, ProfileUpdateBody profileUpdateBody, MultipartFile profileImageFile,MultipartFile backgroundImageFile){
        User user = userRepository.findByUserSeq(userSeq).orElseThrow(() -> new NotFoundException("aSDFA"));
        /*
         프론트와 상의해 봐야함니다.
         */
        return null;
    }
}
