package com.nftfont.module.web3j;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.Collections;

public class SignUtil {

    private static final Log log = LogFactory.getLog(SignUtil.class);
    // You do not need that static values
    //private static final String GETH_SIGN_PREFIX = "\u0019Ethereum Signed Message:\n32"; //"\u0019Ethereum Signed Message:\n"
    //private static final String GETH_SIGN_PREFIX = "\u0019Ethereum Signed Message:\n";

    /**
     * This method is expecting the signed message to be a hash of the original message. The length of the message is
     * then hardcoded to 32. Also, this might only work for messages signed by geth, not sure if other clients
     * add the prefix to the signed message.
     * @param signedHash
     * @param originalmessage
     * @return
     * @throws SignatureException
     */
    public static String getAddressUsedToSignHashedMessage(String signedHash, String originalmessage) throws SignatureException {

        String a = Numeric.toHexString(originalmessage.getBytes());
        System.out.println(a+"마마마ㅏㅁ");
        byte[] messageHashBytes = Numeric.hexStringToByteArray(a);
        String r = signedHash.substring(0, 66);
        String s = "0x"+signedHash.substring(66, 130);
        int iv = Integer.parseUnsignedInt(signedHash.substring(130, 132),16);
        // Version of signature should be 27 or 28, but 0 and 1 are also (!)possible
        if (iv < 27) {
            iv += 27;
        }
        String v = "0x"+ Integer.toHexString(iv);//
        log.info(v);

        byte[] msgBytes = new byte[messageHashBytes.length];
        System.arraycopy(messageHashBytes, 0, msgBytes, 0, messageHashBytes.length);

        String pubkey = Sign.signedPrefixedMessageToKey(msgBytes,
                        new Sign.SignatureData(Numeric.hexStringToByteArray(v)[0],
                                Numeric.hexStringToByteArray(r),
                                Numeric.hexStringToByteArray(s)))
                .toString(16);

        log.debug("Pubkey: " + pubkey);
        return Keys.getAddress(pubkey);
    }

    private static String asciiToHex(String asciiValue)
    {
        char[] chars = asciiValue.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++)
        {
            hex.append(Integer.toHexString((int) chars[i]));
        }

        return hex.toString() + "".join("", Collections.nCopies(32 - (hex.length()/2), "00"));
    }
}