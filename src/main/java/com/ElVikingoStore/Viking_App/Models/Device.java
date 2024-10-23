package com.ElVikingoStore.Viking_App.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;
@Schema(
        description = "Modelo de dispositivo",
        title = "Device"
)
@Entity
@Table(name = "device")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {
    @Schema(
            description = "Identificador único del dispositivo",
            example = "123e4567-e89b-12d3-a456-426614174000",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Schema(
            description = "Tipo de dispositivo",
            example = "smartphone",
            required = true,
            minLength = 2,
            maxLength = 100
    )
    @Column(name = "device_type")
    private String type;
    @Schema(
            description = "Marca del dispositivo",
            example = "Samsung",
            required = true,
            minLength = 2,
            maxLength = 100
    )
    @Column(name = "device_brand")
    private String brand;
    @Schema(
            description = "Modelo del dispositivo",
            example = "Galaxy S20",
            required = true,
            minLength = 2,
            maxLength = 100
    )
    @Column(name = "device_model")
    private String model;
    @Schema(
            description = "Número de serie del dispositivo",
            example = "1234567890",
            required = true,
            minLength = 2,
            maxLength = 100
    )
    @Column(name = "serial_number")
    private String serialNumber;
    @Schema(
            description = "Identificador único del usuario dueño del dispositivo",
            example = "123e4567-e89b-12d3-a456-426614174000",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    // Relación con User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference // Evita recursión al serializar la relación con User
    private User user;

    public Device(UUID id) {
        this.id = id;
    }
}
