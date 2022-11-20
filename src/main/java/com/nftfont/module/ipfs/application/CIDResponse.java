package com.nftfont.module.ipfs.application;

import lombok.Data;
import lombok.Getter;
import pinata.PinataResponse;

@Data
@Getter
public class CIDResponse {
    private PinataResponse pinataResponse;

    public CIDResponse(PinataResponse pinataResponse){
        this.pinataResponse = pinataResponse;
    }
}
