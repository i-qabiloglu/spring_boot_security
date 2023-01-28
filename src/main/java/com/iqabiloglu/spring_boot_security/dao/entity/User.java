package com.iqabiloglu.spring_boot_security.dao.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String username;
    String password;
    @Column(name = "full_name")
    String fullName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") })
    List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities( ) {
        return Optional.ofNullable(roles)
                       .stream()
                       .flatMap(Collection::stream)
                       .map(role -> new SimpleGrantedAuthority(role.getName()))
                       .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired( ) {
        return true;
    }

    @Override
    public boolean isAccountNonLocked( ) {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired( ) {
        return true;
    }

    @Override
    public boolean isEnabled( ) {
        return true;
    }
}
