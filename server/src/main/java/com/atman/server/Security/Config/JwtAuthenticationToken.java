package com.atman.server.Security.Config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String token;
    private final Object principal;

    // Used before authentication (carries the raw JWT string)
    public JwtAuthenticationToken(String token) {
        super((Collection<? extends GrantedAuthority>) null);
        this.token = token;
        this.principal = null;
        setAuthenticated(false);
    }

    // Used after successful authentication (carries UserDetails & roles)
    public JwtAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities, String token) {
        super(authorities);
        this.principal = principal;
        this.token = token;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}