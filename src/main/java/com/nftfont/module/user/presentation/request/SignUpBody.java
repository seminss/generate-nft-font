package com.nftfont.module.user.presentation.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class SignUpBody {
    @NotNull
    private String walletAddress;
    @NotNull
    private String userName;
    @NotNull
    @Email
    private String email;
//    private MultipartFile profileImage;
//    private MultipartFile backgroundImage;
    private String selfDescription;

}
