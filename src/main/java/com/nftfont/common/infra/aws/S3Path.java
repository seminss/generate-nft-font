package com.nftfont.common.infra.aws;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public enum S3Path {
    USER_PROFILE("user/profile"),

    USER_BACKGROUND("user/background");

    private final String path;
}
