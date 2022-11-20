package com.nftfont.module.ipfs.application;

import lombok.Data;

@Data
public class CustomPinataResponse {
    public Boolean IsDuplicate;
    public String IpfsHash;
    public Long PinSize;
    public String Timestamp;
}
