package com.nftfont.module.font.font.dto;

import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.user.domain.userprofile.UserProfile;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FontDetailDto {
    private Long id;
    private String creator;
    private String ttfUrl;


}
