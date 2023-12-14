package com.pm.spring.ema.elastic.query.service.security;

import com.pm.spring.ema.elastic.query.service.business.QueryUserService;
import com.pm.spring.ema.elastic.query.service.transformer.UserPermissionsToUserDetailTransformer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TwitterQueryUserDetailsService implements UserDetailsService {

    private final QueryUserService queryUserService;

    private final UserPermissionsToUserDetailTransformer userPermissionsToUserDetailTransformer;

    public TwitterQueryUserDetailsService(QueryUserService userService,
                                          UserPermissionsToUserDetailTransformer transformer) {
        this.queryUserService = userService;
        this.userPermissionsToUserDetailTransformer = transformer;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return queryUserService
                .findAllPermissionsByUsername(username)
                .map(userPermissionsToUserDetailTransformer::getUserDetails)
                .orElseThrow(
                        () -> new UsernameNotFoundException("No user found with name " + username));
    }
}
