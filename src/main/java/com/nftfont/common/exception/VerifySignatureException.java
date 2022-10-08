package com.nftfont.common.exception;

public class VerifySignatureException extends RuntimeException{
    public VerifySignatureException(String msg, Throwable t) {
        super(msg,t);
    }
    public VerifySignatureException(String msg) {
        super(msg);
    }
    public VerifySignatureException() {
        super();
    }
}