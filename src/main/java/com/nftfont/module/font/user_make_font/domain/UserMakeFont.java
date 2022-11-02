package com.nftfont.module.font.user_make_font.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nftfont.domain.file.image_file.ImageFileObject;
import com.nftfont.module.user.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_make_font")
public class UserMakeFont {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ", nullable = false, insertable = false, updatable = false)
    private User user;

    @OneToOne()
    private ImageFileObject imageFileObject;

    private String imageUrl;

    private String word;

}
