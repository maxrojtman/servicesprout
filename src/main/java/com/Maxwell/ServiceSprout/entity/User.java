package com.Maxwell.ServiceSprout.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Password is required")
    private String password;

    private String role; 


    @ManyToMany(mappedBy = "assignedUsers", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<WorkOrder> assignedWorkOrders = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override public String getUsername() { return email; }
    @Override public String getPassword() { return password; }

    // // These can be adjusted for account status logic
    // @Override public boolean isAccountNonExpired() { return true; }
    // @Override public boolean isAccountNonLocked() { return true; }
    // @Override public boolean isCredentialsNonExpired() { return true; }
    // @Override public boolean isEnabled() { return true; }
}

