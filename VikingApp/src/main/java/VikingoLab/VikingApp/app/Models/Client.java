package VikingoLab.VikingApp.app.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Setter
@Getter
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

    // Cambiado a String y agregado la restricción UNIQUE
    @Column(name = "dni")
    private int dni; // Asegúrate de cambiar el ipo de dato aquí

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "secondary_phone_number")
    private String secondaryPhoneNumber;


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
