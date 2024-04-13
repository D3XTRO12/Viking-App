package VikingoLab.VikingApp.app.models.Staff;

import VikingoLab.VikingApp.app.models.Job.JobModel;

public class StaffBuilder {

    private StaffModel staff;

    public StaffBuilder() {
        this.staff = new StaffModel();
    }

    public StaffBuilder setId(Long id) {
        staff.setId(id);
        return this;
    }

    public StaffBuilder setName(String name) {
        staff.setName(name);
        return this;
    }

    // Corregido para llamar al método correcto
    public StaffBuilder setJob(JobModel job) {
        staff.setJob(job); // Cambiado de setJobId a setJob
        return this;
    }

    public StaffBuilder setRole(String role) {
        staff.setRole(role);
        return this;
    }

    // Corregido para pasar un String en lugar de Long
    public StaffBuilder setPhoneNumber(String phoneNumber) { // Cambiado de Long a String
        staff.setPhoneNumber(phoneNumber); // Asegúrate de que phoneNumber es de tipo String
        return this;
    }

    public StaffBuilder setEmail(String email) {
        staff.setEmail(email);
        return this;
    }

    public StaffBuilder setAddress(String address) {
        staff.setAddress(address);
        return this;
    }

    public StaffModel build() {
        return staff;
    }
}
