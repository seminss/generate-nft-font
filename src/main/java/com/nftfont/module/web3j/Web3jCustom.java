package com.nftfont.module.web3j;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Numeric;

@Service
@RequiredArgsConstructor
@Slf4j
public class Web3jCustom {

    public boolean validateUserAddress(String address){
        if( WalletUtils.isValidAddress(address)){
            return true;
        }
        return false;
    }

    public byte[] getPublicKey() {
        ECKeyPair keyPair =null;
        byte[] publicKey = Numeric.toBytesPadded(keyPair.getPublicKey(),2048);
        return publicKey;
    }


}
