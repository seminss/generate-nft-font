package com.nftfont.common.annotation;

import com.nftfont.domain.userprincipal.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component("apiSecurityChecker")
public class ApiSecurityChecker {

    public boolean hasUserPermission(Authentication authentication, Long id) {
        long memberId = ((UserPrincipal)authentication.getPrincipal()).getId();

        return memberId == id
                &&
                authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()).contains("ROLE_USER");
    }

}