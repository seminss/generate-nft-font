package com.nftfont.common.infra.aws;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public enum S3Path {
    USER_PROFILE("user/profile/profile_image/"),
    USER_BACKGROUND("user/profile/background/"),
    NFT_FONT("font/");
    private final String path;
}
