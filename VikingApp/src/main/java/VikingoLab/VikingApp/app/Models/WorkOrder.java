package VikingoLab.VikingApp.app.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "work_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_dni", referencedColumnName = "dni", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device deviceId;

    @Column(name = "issue_description")
    private String issueDescription;

    @Column(name = "repair_status")
    private String repairStatus;

    @Column(name = "photos")
    private String photos;

    @Column(name = "videos")
    private String videos;

    @Column(name = "notes")
    private String notes;

    // Elimina el constructor vacío y el constructor con el builder, ya que Lombok los generará automáticamente.
}
