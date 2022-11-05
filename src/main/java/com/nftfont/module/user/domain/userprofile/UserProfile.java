package com.nftfont.module.user.domain.userprofile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nftfont.module.file.image_file.ImageFileDto;
import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.user.domain.user.User;
import com.nftfont.module.user.dto.UserProfileCreation;
import com.nftfont.module.user.dto.UserProfileUpdate;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "user_profile")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserProfile {
    @JsonIgnore
    @Id
    @Column(name = "user_profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(name = "user_name",length = 100,nullable = false)
    @Size(max = 100)
    private String username;

    @Column(name = "user_email",length = 512, unique = true,nullable = false)
    @Size(max = 512)
    @Email(message = "이메일 형식이 아니에요!")
    private String email;

    @OneToMany(mappedBy = "userProfile",fetch = FetchType.LAZY)
    private Set<NftFont> nftFonts = new LinkedHashSet<>();

    @Column(name = "image_url")
    private String profileImageUrl;

    @Column(name = "background_image_url")
    private String backgroundImageUrl;

    @Column(name = "self_description")
    private String selfDescription;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    public static UserProfile ofCreation(UserProfileCreation.RequestDto request, User user, ImageFileDto profile,ImageFileDto background){
        return UserProfile.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .user(user)
                .profileImageUrl(profile.getImageUrl())
                .backgroundImageUrl(background.getImageUrl())
                .selfDescription(request.getSelfDescription())
                .build();
    }

    public UserProfile copyWith(UserProfileUpdate.RequestDto request,ImageFileDto profile,ImageFileDto background){
        if(request.getSelfDescription()!=null){
            this.setSelfDescription(request.getSelfDescription());
        }
        if(request.getUsername()!=null){
            this.setUsername(request.getUsername());
        }
        if(request.getEmail()!=null){
            this.setEmail(request.getEmail());
        }
        if(profile!=null){
            this.profileImageUrl = profile.getImageUrl();
        }
        if(background!=null){
            this.backgroundImageUrl = background.getImageUrl();
        }
        return this;
    }
}
