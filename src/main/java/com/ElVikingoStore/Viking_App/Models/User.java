package com.ElVikingoStore.Viking_App.Models;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
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

@Schema(description = "Entidad que representa un usuario en el sistema")
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails, java.io.Serializable {

    @Schema(description = "Identificador único del usuario", example = "123e4567-e89b-12d3-a456-426614174000")
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez", required = true)
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(description = "Documento Nacional de Identidad", example = "12345678", required = true)
    @Column(name = "dni", unique = true, nullable = false)
    private Integer dni;

    @Schema(
            description = "Tipo de usuario en el sistema",
            example = "end-user",
            allowableValues = {"end-user", "company", "staff"},
            required = true
    )
    @Column(name = "user_type", nullable = false)
    private String userType;

    @Schema(description = "Dirección del usuario", example = "Av. Siempreviva 742", required = true)
    @Column(name = "address", nullable = false)
    private String address;

    @Schema(description = "Número de teléfono principal", example = "+54 11 1234-5678", required = true)
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Schema(description = "Número de teléfono secundario", example = "+54 11 8765-4321")
    @Column(name = "secondary_phone_number")
    private String secondaryPhoneNumber;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@email.com")
    @Column(name = "email")
    private String email;

    @Schema(
            description = "Contraseña del usuario (requerida para company y staff)",
            example = "hashedPassword123",
            accessMode = Schema.AccessMode.WRITE_ONLY
    )
    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Schema(
            description = "CUIT de la empresa (solo para usuarios tipo company)",
            example = "20-12345678-9"
    )
    @JsonIgnore
    @Column(name = "cuit", unique = true)
    private String cuit;

    @Schema(description = "Órdenes de trabajo donde el usuario es cliente")
    @OneToMany(mappedBy = "client")
    @JsonManagedReference("clientReference")
    @JsonIgnore
    private List<WorkOrder> workOrdersAsClient;

    @Schema(description = "Órdenes de trabajo donde el usuario es staff")
    @OneToMany(mappedBy = "staff")
    @JsonIgnore
    private List<WorkOrder> workOrdersAsStaff;

    @Schema(description = "Dispositivos asociados al usuario")
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @JsonIgnore
    private List<Device> devices;

    @Schema(description = "Roles asignados al usuario")
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();


    @Schema(description = "Agrega un rol al usuario")
    public void addRole(Role role) {
        UserRole userRole = new UserRole();
        userRole.setUser(this);
        userRole.setRole(role);
        userRoles.add(userRole);
    }
    @Schema(description = "Quita un rol al usuario")
    public void removeRole(Role rol) {
        userRoles.removeIf(userRole -> userRole.getRole().equals(rol));
    }

    @Schema(description = "Obtiene Los Roles del usuario")
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