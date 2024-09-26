package com.ElVikingoStore.Viking_App.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode(of = "id")
public class Rol implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rol_id")
	private Long id;

	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@Column(name = "permiso", nullable = false)
	private String permiso;

	@OneToMany(mappedBy = "rol")
	private Set<UserRole> userRoles = new HashSet<>();

}