package com.ElVikingoStore.Viking_App.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@Entity
@Table(name = "user_roles")
public class UserRole {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference // Parte inversa de la relación con User
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonBackReference // Parte inversa de la relación con Role
    private Role role;

    public UUID getRoleId() {
        return role != null ? role.getId() : null; // Manejo de null
    }
}