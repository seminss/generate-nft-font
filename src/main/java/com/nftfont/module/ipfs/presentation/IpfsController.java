package com.nftfont.module.ipfs.presentation;

import com.nftfont.config.security.CurrentUser;
import com.nftfont.module.ipfs.application.IpfsService;
import com.nftfont.module.ipfs.dto.TTFPinning;
import com.nftfont.module.user.domain.user.User;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class IpfsController {
    private final IpfsService ipfsService;

    @PostMapping(value ="/ipfs/pinning",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void pinning(@Parameter(hidden = true) @CurrentUser User user,
                        @RequestPart MultipartFile ttfFile,@RequestPart TTFPinning ttfPinning){


    }
}
