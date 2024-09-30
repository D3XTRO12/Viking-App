package com.ElVikingoStore.Viking_App.Models;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "dni", unique = true, nullable = false)
    private Integer dni;

    @Column(name = "user_type", nullable = false)
    private String userType; // 'end-user', 'company', 'staff'

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "secondary_phone_number")
    private String secondaryPhoneNumber;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password; // Opcional para 'end-user', obligatorio para 'company' y 'staff'

    @JsonIgnore
    @Column(name = "cuit", unique = true)
    private String cuit; // Solo para 'company'

    @OneToMany(mappedBy = "client") // Relación inversa con WorkOrder como cliente
    @JsonManagedReference("clientReference")
    @JsonIgnore
    private List<WorkOrder> workOrdersAsClient;

    @OneToMany(mappedBy = "staff") // Relación inversa con WorkOrder como staff
    @JsonIgnore
    private List<WorkOrder> workOrdersAsStaff;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference // Para serializar los dispositivos del usuario
    @JsonIgnore
    private List<Device> devices;


    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @JsonManagedReference // Rompe el ciclo de referencia con UserRole
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();


    public void addRole(Role role) {
        UserRole userRole = new UserRole();
        userRole.setUser(this);
        userRole.setRole(role);
        userRoles.add(userRole);
    }

    public void removeRole(Role rol) {
        userRoles.removeIf(userRole -> userRole.getRole().equals(rol));
    }
    @JsonIgnore
    public Set<Role> getRoles() {
        return userRoles.stream()
                .map(UserRole::getRole)
                .collect(Collectors.toSet());
    }
    public boolean hasRole(String roleName) {
        return getRoles().stream()
                .anyMatch(rol -> rol.getDescripcion().equals(roleName));
    }
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getPermission()))
                .collect(Collectors.toList());
    }
    @JsonIgnore
    private boolean accountNonExpired;
    @JsonIgnore
    private boolean accountNonLocked;
    @JsonIgnore
    private boolean credentialsNonExpired;
    @JsonIgnore
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