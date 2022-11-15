package com.nftfont.module.font.font.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public enum SupportType {

    KO_ER("한글+영어"),
    ER("영어"),
    KR("한글");
    private String text;
}
