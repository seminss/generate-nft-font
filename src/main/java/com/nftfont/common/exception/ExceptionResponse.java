package com.nftfont.common.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponse {
    private String type;
    private String msg;

    public static ExceptionResponse of(Exception exception){
        return ExceptionResponse.builder()
                .type(exception.getClass().getSimpleName())
                .msg(exception.getMessage())
                .build();
    }
}
