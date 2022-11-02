package com.nftfont.domain.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public enum FilePath {
    PROFILE("user/profile/profile_image/"),
    BACKGROUND("user/profile/background/");

    private final String path;
}
