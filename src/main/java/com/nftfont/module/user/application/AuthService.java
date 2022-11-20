package com.nftfont.module.user.application;


import com.nftfont.common.exception.ConflictException;
import com.nftfont.common.exception.VerifySignatureException;
import com.nftfont.common.jwt.JwtToken;
import com.nftfont.common.jwt.JwtTokenProvider;
import com.nftfont.common.utils.CookieUtil;
import com.nftfont.common.utils.HeaderUtil;
import com.nftfont.config.properties.AppProperties;
import com.nftfont.module.user.domain.user.User;
import com.nftfont.module.user.domain.user.UserRefreshToken;
import com.nftfont.module.user.domain.userprincipal.RoleType;
import com.nftfont.module.user.domain.user.UserRepository;
import com.nftfont.module.user.domain.userprofile.UserProfile;
import com.nftfont.module.user.domain.userprofile.UserProfileRepository;
import com.nftfont.module.user.dto.UserLoginInfo;
import com.nftfont.module.user.domain.user.UserRefreshTokenRepository;
import com.nftfont.module.user.dto.AccessTokenResponse;
import com.nftfont.module.web3j.Web3jCustom;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SignatureException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final Web3jCustom web3jCustom;
    private final AppProperties appProperties;
    private final String personalMessagePrefix = "Ethereum Signed Message:";
    private final String hexPrefix = "0x";
    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";
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

    public AccessTokenResponse.ResponseDto verifySignature(AccessTokenResponse.RequestDto request,HttpServletResponse response)
            throws SignatureException {

        Optional<User> optionalUser = userRepository.findByWalletAddress(request.getWalletAddress());
        User user;

        Boolean isFirstAttempt = false;
        if(optionalUser.isEmpty()){
            user=User.ofCreate(request.getWalletAddress(),null);
            UserProfile userProfile = UserProfile.ofUser(user);
            isFirstAttempt = true;
            userRepository.save(user);userProfileRepository.save(userProfile);
        }else{
            user=optionalUser.get();
        }

        String publicAddress = getAddressUsedToSignHashedMessage(request.getSignature(), "eternal_semin");

        if(!publicAddress.equals(user.getWalletAddress().toLowerCase())){
            throw new VerifySignatureException("서명 검증이 실패했어요");
        }

        // 서명검증에 성공함
        updateNonce(user);

        // 토큰 발급
        Date now = new Date();
        JwtToken accessToken = jwtTokenProvider.createJwtToken(user.getId().toString(), RoleType.USER.getCode(),
                new Date(now.getTime()+appProperties.getAuth().getTokenExpiry()));

        JwtToken refreshToken = jwtTokenProvider.createJwtToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime()+appProperties.getAuth().getRefreshTokenExpiry())
        );


        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(user.getId().toString());

        if(userRefreshToken!=null){
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        }else{
            userRefreshToken = new UserRefreshToken(user.getId().toString(), refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        }

        setCookie(response, refreshToken.getToken());

        return AccessTokenResponse.ResponseDto.ofSuccess(accessToken.getToken(),user.getId(),isFirstAttempt);


    }

    public AccessTokenResponse.ResponseDto refreshToken(HttpServletRequest request, HttpServletResponse response, String refreshToken){
        String accessToken = HeaderUtil.getAccessToken(request);
        JwtToken authToken = jwtTokenProvider.convertJwtToken(accessToken);

        if(!authToken.validate()){
            return null;
        }

        Claims claims = authToken.getTokenClaims();
        if(claims == null){
            return null;
        }

        String userId = claims.getSubject();
        RoleType roleType = RoleType.of(claims.get("role",String.class));

        JwtToken authRefreshToken = jwtTokenProvider.convertJwtToken(refreshToken);

        if(authRefreshToken.validate()){
            return null;
        }

        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserIdAndRefreshToken(userId,refreshToken);
        if(userRefreshToken == null){
            return null;
        }
        Date now = new Date();
        JwtToken newAccessToken = jwtTokenProvider.createJwtToken(
                userId,roleType.getCode(),new Date(now.getTime()+appProperties.getAuth().getTokenExpiry())
        );

        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        if (validTime <= THREE_DAYS_MSEC) {
            // refresh 토큰 설정
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            authRefreshToken = jwtTokenProvider.createJwtToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + refreshTokenExpiry)
            );

            // DB에 refresh 토큰 업데이트
            userRefreshToken.setRefreshToken(authRefreshToken.getToken());

            int cookieMaxAge = (int) refreshTokenExpiry / 60;
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
        }

        return AccessTokenResponse.ResponseDto.ofSuccess(newAccessToken.getToken());
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
    private void setCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookieHeader = ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .path("/users/auth/refresh")
                .maxAge(appProperties.getAuth().getRefreshTokenExpiry())
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookieHeader.toString());
    }
}
