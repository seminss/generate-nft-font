package com.nftfont.module.user.application;


import com.nftfont.common.exception.ConflictException;
import com.nftfont.common.exception.VerifySignatureException;
import com.nftfont.common.jwt.JwtTokenProvider;
import com.nftfont.domain.user.user.User;
import com.nftfont.domain.user.userprofile.UserProfile;
import com.nftfont.domain.user.userprofile.UserProfileRepository;
import com.nftfont.module.file.image_file.application.ImageFileService;
import com.nftfont.domain.user.user.UserRepository;
import com.nftfont.module.user.dto.UserLoginInfo;
import com.nftfont.domain.user.user.UserRefreshTokenRepository;
import com.nftfont.module.user.dto.UserProfileCreation;
import com.nftfont.module.user.dto.UserSignature;
import com.nftfont.module.web3j.SignUtil;
import com.nftfont.module.web3j.Web3jCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import javax.transaction.Transactional;
import java.security.SignatureException;
import java.util.Optional;
import java.util.UUID;

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

    private final String personalMessagePrefix = "Ethereum Signed Message:";
    private final String hexPrefix = "0x";
    public UserLoginInfo.ResponseDto signUpWithWallet(UserLoginInfo.RequestDto request){
        if(!web3jCustom.validateUserAddress(request.getWalletAddress())) {
            throw new ConflictException("유효하지 않은 지갑주소입니다.");
        }
        Optional<User> optionalUser = userRepository.findByWalletAddress(request.getWalletAddress());

        if(optionalUser.isPresent()){
            return UserLoginInfo.ResponseDto.of(optionalUser.get());
        }

        // 처음 요청한 것이라면 (회원가입?..)
        String nonce = personalMessagePrefix+UUID.randomUUID();
        User savedUser = userRepository.save(User.ofCreate(request.getWalletAddress(), nonce));
        return UserLoginInfo.ResponseDto.of(savedUser);
    }

    public UserSignature.ResponseDto verifySignature(UserSignature.RequestDto request) throws SignatureException {

        User user = userRepository.findByWalletAddress(request.getWalletAddress()).orElseThrow(()-> new ConflictException("해당 지갑주소를 가진 유저가 없습니다."));
        String publicAddress = getAddressUsedToSignHashedMessage(request.getSignature(), user.getNonce());

        // 서명검증에 성공함
        if(publicAddress.equals(user.getWalletAddress().toLowerCase())){
            updateNonce(user);
            // 토큰 발급
        }

        throw new VerifySignatureException("서명 검증이 실패했어요");
    }


    public String getAddressUsedToSignHashedMessage(String signedHash, String originalMessage) throws SignatureException {

        String a = Numeric.toHexString(originalMessage.getBytes());
        byte[] messageHashBytes = Numeric.hexStringToByteArray(a);
        String r = signedHash.substring(0, 66);
        String s = hexPrefix+signedHash.substring(66, 130);
        int iv = Integer.parseUnsignedInt(signedHash.substring(130, 132),16);
        // Version of signature should be 27 or 28, but 0 and 1 are also (!)possible
        if (iv < 27) {
            iv += 27;
        }
        String v = hexPrefix+ Integer.toHexString(iv);//
        log.info(v);

        byte[] msgBytes = new byte[messageHashBytes.length];
        System.arraycopy(messageHashBytes, 0, msgBytes, 0, messageHashBytes.length);

        String publicKey = Sign.signedPrefixedMessageToKey(msgBytes,
                        new Sign.SignatureData(Numeric.hexStringToByteArray(v)[0],
                                Numeric.hexStringToByteArray(r),
                                Numeric.hexStringToByteArray(s)))
                .toString(16);

        log.debug("Pubkey: " + publicKey);
        return hexPrefix+Keys.getAddress(publicKey);
    }

    private void updateNonce(User user){
        String newNonce = personalMessagePrefix+UUID.randomUUID();
        user.setNonce(newNonce);
        userRepository.save(user);
    }
}
