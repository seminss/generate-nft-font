package com.nftfont.domain.user.userprofile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nftfont.domain.font.font.NftFont;
import com.nftfont.domain.user.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
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
}
