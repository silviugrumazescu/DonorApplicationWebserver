package com.example.donationapp.security;

import com.example.donationapp.model.District;
import com.example.donationapp.model.Role;
import com.example.donationapp.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private String name;
    private String email;
    private String password;
    private District district;
    private String CNP;
    private String role;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String name, String email, String password, District district, String CNP, Role role) {
        List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
        roles.add(new SimpleGrantedAuthority(role.name()));

        this.name = name;
        this.email = email;
        this.password = password;
        this.district = district;
        this.CNP = CNP;
        this.authorities = roles;
        this.role = role.name();
    }

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getDistrict(),
                user.getCNP(),
                user.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getRole() {
        return role;
    }

}
