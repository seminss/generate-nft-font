package com.nftfont.module.user.presentation;
import com.nftfont.common.dto.ApiResult;
import com.nftfont.common.exception.ConflictException;
import com.nftfont.domain.user.user.User;
import com.nftfont.domain.user.user.UserRepository;
import com.nftfont.module.user.application.AuthService;
import com.nftfont.module.user.dto.UserLoginInfo;
import com.nftfont.module.user.dto.UserSignature;
import com.nftfont.module.web3j.SignUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.SignatureException;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    @PostMapping("/users/signIn")
    public ApiResult<UserLoginInfo.ResponseDto> signIn(@RequestBody @Valid UserLoginInfo.RequestDto request){
        UserLoginInfo.ResponseDto responseDto = authService.signUpWithWallet(request);
        ApiResult<UserLoginInfo.ResponseDto> apiResult = ApiResult.success(responseDto);
        return apiResult;
    }

    @PostMapping("/users/auth/signature")
    public ApiResult<UserSignature.ResponseDto> verifySignIn(@RequestBody @Valid UserSignature.RequestDto request) throws SignatureException {
        UserSignature.ResponseDto responseDto = authService.verifySignature(request);
        User user = userRepository.findByWalletAddress(request.getWalletAddress()).orElseThrow(()-> new ConflictException("없서요"));
        String addressUsedToSignHashedMessage = SignUtil.getAddressUsedToSignHashedMessage(request.getSignature(), user.getNonce());
        System.out.println(addressUsedToSignHashedMessage+"될가요");
//        User user = userRepository.findByWalletAddress(request.getWalletAddress()).orElseThrow(()-> new ConflictException("없서요"));
//        String addressUsedToSignHashedMessage = authService.getAddressUsedToSignHashedMessage(request.getSignature(), user.getNonce());
//        System.out.println(addressUsedToSignHashedMessage+"제발돼라");
        return null;
    }
}

