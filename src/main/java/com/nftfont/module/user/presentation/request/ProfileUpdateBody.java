package com.nftfont.module.user.presentation.request;

import lombok.Data;

@Data
public class ProfileUpdateBody {
    private String name;
    private String selfDescription;
}
