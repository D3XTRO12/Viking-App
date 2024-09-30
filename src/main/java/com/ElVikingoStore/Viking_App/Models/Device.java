package com.ElVikingoStore.Viking_App.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "device")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "device_type")
    private String type;

    @Column(name = "device_brand")
    private String brand;

    @Column(name = "device_model")
    private String model;

    @Column(name = "serial_number")
    private String serialNumber;

    // Relación con User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference // Evita recursión al serializar la relación con User
    private User user;

    public Device(UUID id) {
        this.id = id;
    }
}
