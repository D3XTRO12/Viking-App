package VikingoLab.VikingApp.app.Models;


import jakarta.persistence.*;

@Entity
@Table(name = "jobs")
public class JobModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clients_id") // Asegúrate de que este nombre coincida con el nombre de la columna en tu base de datos
    private ClientModel clientId;

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

    public JobModel(JobBuilder builder) {
        this.clientId = builder.clientId;
        this.deviceType = builder.deviceType;
        this.deviceBrand = builder.deviceBrand;
        this.deviceModel = builder.deviceModel;
        this.serialNumber = builder.serialNumber;
        this.issueDescription = builder.issueDescription;
        this.repairStatus = builder.repairStatus;
        this.photos = builder.photos;
        this.videos = builder.videos;
        this.notes = builder.notes;
    }

    public static class JobBuilder{
        private ClientModel clientId;
        private String deviceType;
        private String deviceBrand;
        private String deviceModel;
        private String serialNumber;
        private String issueDescription;
        private String repairStatus;
        private String photos;
        private String videos;
        private String notes;
        
        public JobBuilder (){
        }
        public JobBuilder setDeviceType(String deviceType){
            this.deviceType = deviceType;
            return this;
        }
        public JobBuilder setDeviceBrand(String deviceBrand){
            this.deviceBrand = deviceBrand;
            return this;
        }
        public JobBuilder setDeviceModel(String deviceModel){
            this.deviceModel = deviceModel;
            return this;
        }
        public JobBuilder setSerialNumber(String serialNumber){
            this.serialNumber = serialNumber;
            return this;
        }
        public JobBuilder setIssueDescription(String issueDescription){
            this.issueDescription = issueDescription;
            return this;
        }
        public JobBuilder setRepairStatus(String repairStatus){
            this.repairStatus = repairStatus;
            return this;
        }
        public JobBuilder setPhotos(String photos){
            this.photos = photos;
            return this;
        }
        public JobBuilder setVideos(String videos){
            this.videos = videos;
            return this;
        }
        public JobBuilder setNotes(String notes){
            this.notes = notes;
            return this;
        }
        public JobBuilder setClientId(ClientModel clientId){
            this.clientId = clientId;
            return this;
        }
        public JobModel build(){
            return new JobModel(this);
        }
        
    }

    
}
