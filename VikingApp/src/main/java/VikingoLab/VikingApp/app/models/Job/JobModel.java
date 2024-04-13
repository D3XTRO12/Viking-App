package VikingoLab.VikingApp.app.models.Job;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jobs")
public class JobModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "device_brand")
    private String deviceBrand;

    @Column(name = "device_model")
    private String deviceModel;

    @Column(name = "serial_number")
    private String serialNumber;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setRepairStatus(String repairStatus) {
        this.repairStatus = repairStatus;
    }

    public String getRepairStatus() {
        return repairStatus;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getPhotos() {
        return photos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getVideos() {
        return videos;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }
}
