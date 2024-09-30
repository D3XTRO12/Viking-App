package com.ElVikingoStore.Viking_App.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "diagnostic_points")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticPoint {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // Relación con la tabla de WorkOrder
    @ManyToOne
    @JoinColumn(name = "work_order_id", nullable = false)
    @JsonBackReference
    private WorkOrder workOrder;  // Asegura la relación con la entidad WorkOrder modificada

    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    @Column(name = "description", nullable = false)
    private String description;

    // Archivos multimedia asociados con este punto de diagnóstico
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "multimedia_files")
    @Builder.Default
    private List<String> multimediaFiles = new ArrayList<>();

    @Column(name = "notes")
    private String notes;
}
