package com.nftfont.module.user.domain.userprofile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nftfont.module.file.image_file.ImageFileDto;
import com.nftfont.module.font.font.domain.NftFont;
import com.nftfont.module.user.domain.user.User;
import com.nftfont.module.user.dto.UserProfileSet;
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

    @Column(name = "image_url",nullable = false)
    private String profileImageUrl;

    @Column(name = "background_image_url",nullable = false)
    private String backgroundImageUrl;

    @Column(name = "self_description")
    private String selfDescription;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
    public static UserProfile ofSet(UserProfileSet.RequestDto request, ImageFileDto profile, ImageFileDto background,
                                    User user,Long id){
        return UserProfile.builder()
                .id(id == null ? null : id)
                .user(user)
                .email(request.getEmail())
                .selfDescription(request.getSelfDescription())
                .username(request.getUsername())
                .backgroundImageUrl(background == null ? null : background.getImageUrl())
                .profileImageUrl(profile == null ? null : profile.getImageUrl())
                .modifiedAt(LocalDateTime.now()).build();
    }
}
