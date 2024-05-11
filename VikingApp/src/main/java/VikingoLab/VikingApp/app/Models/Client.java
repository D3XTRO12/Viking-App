package VikingoLab.VikingApp.app.Models;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "dni")
    private int dni;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "secondary_phone_number")
    private String secondaryPhoneNumber;

    @OneToMany(mappedBy = "client")
    @JsonIgnore // Ignorar esta propiedad al serializar a JSON
    private List<Device> devices;

    @Override
    public String toString() {
        return "ClientModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dni=" + dni +
                ", address='" + address + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", secondaryPhoneNumber=" + secondaryPhoneNumber +
                '}';
    }
}
