package com.nftfont.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {

    private boolean success;

    private T data;

    public static <R> ApiResult<R> success(R data) {
        return ApiResult.<R>builder()
                .success(true)
                .data(data)
                .build();
    }
}
