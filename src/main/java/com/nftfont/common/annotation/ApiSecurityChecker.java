package com.nftfont.common.annotation;

import com.nftfont.config.security.CurrentUser;
import com.nftfont.module.user.domain.userprincipal.RoleType;
import com.nftfont.module.user.domain.userprincipal.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Component("apiSecurityChecker")
public class ApiSecurityChecker {

    public boolean hasUserPermission(@CurrentUser UserAccount account, Long id) {
        return id.equals(account.getUser().getId()) &&
                (account.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()).contains(RoleType.USER.getCode()));
    }

}