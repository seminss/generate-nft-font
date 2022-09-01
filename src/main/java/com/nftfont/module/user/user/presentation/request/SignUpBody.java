package com.nftfont.module.user.user.presentation.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
