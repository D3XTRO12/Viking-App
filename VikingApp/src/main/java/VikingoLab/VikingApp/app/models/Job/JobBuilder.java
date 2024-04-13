package VikingoLab.VikingApp.app.models.Job;


public class JobBuilder {

    private JobModel job;

    public JobBuilder() {
        this.job = new JobModel();
    }

    public JobBuilder setId(Long id) {
        job.setId(id);
        return this;
    }

    public JobBuilder setCustomerId(Long customerId) {
        job.setCustomerId(customerId);
        return this;
    }

    public JobBuilder setDeviceType(String deviceType) {
        job.setDeviceType(deviceType);
        return this;
    }

    public JobBuilder setDeviceBrand(String deviceBrand) {
        job.setDeviceBrand(deviceBrand);
        return this;
    }

    public JobBuilder setDeviceModel(String deviceModel) {
        job.setDeviceModel(deviceModel);
        return this;
    }

    public JobBuilder setSerialNumber(String serialNumber) {
        job.setSerialNumber(serialNumber);
        return this;
    }

    public JobBuilder setIssueDescription(String issueDescription) {
        job.setIssueDescription(issueDescription);
        return this;
    }

    public JobBuilder setRepairStatus(String repairStatus) {
        job.setRepairStatus(repairStatus);
        return this;
    }

    public JobBuilder setPhotos(String photos) {
        job.setPhotos(photos);
        return this;
    }

    public JobBuilder setVideos(String videos) {
        job.setVideos(videos);
        return this;
    }

    public JobBuilder setNotes(String notes) {
        job.setNotes(notes);
        return this;
    }

    public JobModel build() {
        return job;
    }

    public JobModel getJob() {
        return job;
    }
}
