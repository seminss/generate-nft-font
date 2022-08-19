package com.nftfont.core.annotation;

import com.nftfont.core.configuration.properties.AppProperties;
import com.nftfont.module.user.user.domain.user_pricipal.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component("apiSecurityChecker")
public class ApiSecurityChecker {

    public boolean hasUserPermission(Authentication authentication, Long id) {
        return true;
//        long memberId = ((UserPrincipal)authentication.getPrincipal()).getId();
//
//        return memberId == id
//                &&
//                authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()).contains("ROLE_USER");
    }

}