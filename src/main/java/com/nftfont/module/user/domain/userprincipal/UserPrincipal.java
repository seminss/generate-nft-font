package com.nftfont.module.user.domain.userprincipal;

import com.nftfont.module.user.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails, OidcUser {
    private final Long id;
    private final String password;
    private final RoleType roleType;
    private final User user;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public Long getId(){
        return id;
    }
    @Override
    public Map<String, Object> getAttributes(){
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    @Override
    public String getUsername() {
        return "NONE";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
//    public static UserPrincipal create(User user) {
//
//        return new UserPrincipal(
//                user.getId(),
//                "none",
//                RoleType.USER,
//                user,
//                Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode()))
//        );
//    }
    public UserPrincipal(User user){
        this.id = user.getId();
        this.password = "none";
        this.roleType = user.getRoleType();
        this.user = user;
        this.authorities=Collections.singletonList(new SimpleGrantedAuthority(user.getRoleType().getCode()));
    }
//    public static UserPrincipal create(User user, Map<String, Object> attributes) {
//        UserPrincipal userPrincipal = create(user);
//        userPrincipal.setAttributes(attributes);
//
//        return userPrincipal;
//    }

}
