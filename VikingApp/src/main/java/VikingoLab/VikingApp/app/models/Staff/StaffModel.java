package VikingoLab.VikingApp.app.models.Staff;

import VikingoLab.VikingApp.app.models.Job.JobModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "staff")
public class StaffModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // Getters and setters

    public StaffModel() {
    } // Default constructor

    public StaffModel(Long id, String name, JobModel job, String role, String phoneNumber, String email, String address) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    } // Full constructor (optional)

    // Correctly implemented getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobModel getJob() {
        return job;
    }

    public void setJob(JobModel job) {
        this.job = job;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
