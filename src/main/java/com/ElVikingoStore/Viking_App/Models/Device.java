package com.ElVikingoStore.Viking_App.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "device")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "device_type")
    private String type;

    @Column(name = "device_brand")
    private String brand;

    @Column(name = "device_model")
    private String model;

    @Column(name = "serial_number")
    private String serialNumber;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Device(Long id) {
        this.id = id;
    }
}
