package com.ElVikingoStore.Viking_App.Models;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "user_roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    public Long getRolId() {
        return rol != null ? rol.getId() : null; // Manejo de null
    }
}