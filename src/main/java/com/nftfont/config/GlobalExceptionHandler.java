package com.nftfont.config;

import com.nftfont.common.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse authenticationException(HttpServletRequest request, AuthenticationException e){
        return ExceptionResponse.of(e);
    }
    @ExceptionHandler(OAuthProviderMissMatchException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse oauthProviderMissMatchException(OAuthProviderMissMatchException e){
        return ExceptionResponse.of(e);
    }
    @ExceptionHandler(TokenValidFailedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse tokenValidFailedException(TokenValidFailedException e){
        return ExceptionResponse.of(e);
    }


    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse conflictException(ConflictException e){
        return ExceptionResponse.of(e);
    }

    @ExceptionHandler(VerifySignatureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse verifySignatureException(VerifySignatureException e){
        return ExceptionResponse.of(e);
    }

}
