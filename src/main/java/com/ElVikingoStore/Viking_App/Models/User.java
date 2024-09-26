package com.ElVikingoStore.Viking_App.Models;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails, java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id",
            unique = true, 
            nullable = false)
    private Long id;

    @Column(name = "name",
            nullable = false)
    private String name;

    @Column(name = "dni",
            unique = true,
            nullable = false)
    private Integer dni;

    @Column(name = "user_type",
            nullable = false)
    private String userType; // 'end-user', 'company', 'staff'

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "secondary_phone_number")
    private String secondaryPhoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password; // Opcional para 'end-user', obligatorio para 'company' y 'staff'

    @Column(name = "cuit", unique = true)
    private String cuit; // Solo para 'company'

    // Relación con órdenes de trabajo (solo para staff)
    @OneToMany(mappedBy = "staff")
    @JsonIgnore
    private List<WorkOrder> workOrders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new HashSet<>();


    public void addRole(Rol rol) {
        UserRole userRole = new UserRole();
        userRole.setUser(this);
        userRole.setRol(rol);
        userRoles.add(userRole);
    }

    public void removeRole(Rol rol) {
        userRoles.removeIf(userRole -> userRole.getRol().equals(rol));
    }

    public Set<Rol> getRoles() {
        return userRoles.stream()
                .map(UserRole::getRol)
                .collect(Collectors.toSet());
    }
    public boolean hasRole(String roleName) {
        return getRoles().stream()
                .anyMatch(rol -> rol.getDescripcion().equals(roleName));
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRol().getPermiso()))
                .collect(Collectors.toList());
    }

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;


    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

}