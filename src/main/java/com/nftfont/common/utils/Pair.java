package com.nftfont.common.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Pair<L,R> {
    L left;
    R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L,R> Pair<L,R> of(L left, R right){
        return new Pair<L,R>(left, right);
    }
}
