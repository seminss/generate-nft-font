package com.nftfont.core.exception;

public class TokenValidFailedException extends RuntimeException{

    public TokenValidFailedException(){
        super("Failed to generate Token.");
    }

    private TokenValidFailedException(String message){
        super(message);
    }

}
