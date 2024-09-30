package com.ElVikingoStore.Viking_App.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode(of = "id")
public class Role implements Serializable {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@Column(name = "permission", nullable = false)
	private String permission;

	@OneToMany(mappedBy = "role")
	@JsonIgnore // Evita serializar la relación con UserRole para prevenir ciclos
	private Set<UserRole> userRoles = new HashSet<>();
}
