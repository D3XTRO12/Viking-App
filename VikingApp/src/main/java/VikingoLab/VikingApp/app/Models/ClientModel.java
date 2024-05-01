package VikingoLab.VikingApp.app.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class ClientModel{
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
    private Long phoneNumber;

    @Column(name = "secondary_phone_number")
    private Long secondaryPhoneNumber;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    private JobModel job;
    
    public ClientModel(){}

    public ClientModel (ClientBuilder builder){
        this.name = builder.name;
        this.dni = builder.dni;
        this.address = builder.address;
        this.phoneNumber = builder.phoneNumber;
        this.secondaryPhoneNumber = builder.secondaryPhoneNumber;
        this.job = builder.job;
    }

    public static class ClientBuilder{
        private String name;
        private int dni;
        private String address;
        private Long phoneNumber;
        private Long secondaryPhoneNumber;
        private JobModel job;

        public ClientBuilder(){
        }

        public ClientBuilder setName(String name){
            this.name = name;
            return this;
        }

        public ClientBuilder setDni(int dni){
            this.dni = dni;
            return this;
        }

        public ClientBuilder setAddress(String address){
            this.address = address;
            return this;
        }

        public ClientBuilder setPhoneNumber(Long phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ClientBuilder setSecondaryPhoneNumber(Long secondaryPhoneNumber){
            this.secondaryPhoneNumber = secondaryPhoneNumber;
            return this;
        }

        public ClientBuilder setJob(JobModel job) {
            this.job = job;
            return this;
        }

        public ClientModel build(){
            return new ClientModel(this);
        }
        
        
    }

}