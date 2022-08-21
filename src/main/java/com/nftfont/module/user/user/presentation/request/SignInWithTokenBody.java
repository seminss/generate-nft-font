package com.nftfont.module.user.user.presentation.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SignInWithTokenBody {
    @NotEmpty
    private String accessToken;
}
