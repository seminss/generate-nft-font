package com.nftfont.module.font.font.presentation.request;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class GetUserLikeFontParams {
    private String address;
}
