package VikingoLab.VikingApp.app.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "staff")
public class StaffModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER) // Eager fetching for testing purposes
    @JoinColumn(name = "job_id")
    private JobModel job;

    @Column(name = "role")
    private String role;

    @Column(name = "phone_number")
    private String phoneNumber; // Update to String type

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    public StaffModel(StaffBuilder builder) {
        this.name = builder.name;
        this.job = builder.job;
        this.role = builder.role;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.address = builder.address;
    }
    public static class StaffBuilder {

        private String name;
        private JobModel job;
        private String role;
        private String phoneNumber;
        private String email;
        private String address;

        public StaffBuilder() {
        }

        public StaffBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public StaffBuilder setJob(JobModel job) {
            this.job = job;
            return this;
        }

        public StaffBuilder setRole(String role) {
            this.role = role;
            return this;
        }

        public StaffBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public StaffBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public StaffBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public StaffModel build() {
            return new StaffModel(this);
        }
    }
}
