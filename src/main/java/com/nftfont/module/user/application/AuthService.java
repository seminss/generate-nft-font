package com.nftfont.module.user.application;


import com.nftfont.common.exception.ConflictException;
import com.nftfont.common.jwt.JwtTokenProvider;
import com.nftfont.domain.user.user.User;
import com.nftfont.domain.user.userprofile.UserProfile;
import com.nftfont.domain.user.userprofile.UserProfileRepository;
import com.nftfont.module.file.image_file.application.ImageFileService;
import com.nftfont.domain.user.user.UserRepository;
import com.nftfont.module.user.dto.UserCreation;
import com.nftfont.domain.user.user.UserRefreshTokenRepository;
import com.nftfont.module.user.dto.UserProfileCreation;
import com.nftfont.module.web3j.Web3jCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final UserRepository userRepository;
    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";
    private final ImageFileService imageFileService;
    private final Web3jCustom web3jCustom;
    private final UserProfileRepository userProfileRepository;

    public UserCreation.ResponseDto signUpWithWallet(UserCreation.RequestDto request){
        if(!web3jCustom.validateUserAddress(request.getWalletAddress())){
            throw new ConflictException("유효하지 않은 지갑주소 입니다.");
        }

        return UserCreation.ResponseDto.of(userRepository.save(User.of(request)));
    }


}
