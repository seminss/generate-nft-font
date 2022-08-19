package com.nftfont.module.user.user.presentation.request;

import lombok.Data;

@Data
public class ProfileUpdateBody {
    private Long userSeq;
    private String name;
    private String userId;
    private String profileImageUrl;
    private String backgroundImageUrl;
    private String selfDescription;
}
