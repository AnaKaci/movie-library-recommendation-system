package com.example.diplomeBackend.security;

import com.example.diplomeBackend.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return authorities if you have any; returning empty for now
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Change based on your requirements
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Change based on your requirements
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Change based on your requirements
    }

    @Override
    public boolean isEnabled() {
        return true; // Change based on your requirements
    }
}
