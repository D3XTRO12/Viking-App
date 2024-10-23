package com.ElVikingoStore.Viking_App.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;
@Schema(
        description = "Orden de trabajo",
        title = "WorkOrder"
)
@Entity
@Table(name = "work_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrder {
    @Schema(
            description = "Identificador único de la orden de trabajo",
            example = "123e4567-e89b-12d3-a456-426614174000",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Schema(
            description = "Cliente de la orden de trabajo",
            example = "123e4567-e89b-12d3-a456-426614174000",
            required = true
    )
    // Relación con el cliente (end-user o company)
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference("clientReference")
    private User client;
    @Schema(
            description = "Personal que realiza la reparación",
            example = "123e4567-e89b-12d3-a456-426614174000",
            required = true
    )
    // Relación con el staff que realiza la reparación
    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference("staffReference")
    private User staff;
    @Schema(
            description = "Dispositivo asociado a la orden de trabajo",
            example = "123e4567-e89b-12d3-a456-426614174000",
            required = true
    )
    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    @JsonBackReference
    private Device device; // Cambiado de deviceId a device
    @Schema(
            description = "Descripción de la falla",
            example = "Pantalla rota",
            required = true
    )
    @Column(name = "issue_description")
    private String issueDescription;
    @Schema(
            description = "Estado de la reparación",
            example = "pending",
            required = true
    )
    @Column(name = "repair_status")
    private String repairStatus;
    @Schema(
            description = "Puntos de diagnóstico de la orden de trabajo",
            required = true
    )
    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<DiagnosticPoint> diagnosticPoints;


}
